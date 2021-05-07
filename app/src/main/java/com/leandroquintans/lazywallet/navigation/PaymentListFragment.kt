package com.leandroquintans.lazywallet.navigation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import com.leandroquintans.lazywallet.*
import com.leandroquintans.lazywallet.adapters.PaymentListAdapter
import com.leandroquintans.lazywallet.databinding.FragmentPaymentListBinding
import com.leandroquintans.lazywallet.db.AppDatabase
import com.leandroquintans.lazywallet.db.converters.WalletConverter
import com.leandroquintans.lazywallet.viewmodels.PaymentListViewModel
import com.leandroquintans.lazywallet.viewmodels.PaymentListViewModelFactory

class PaymentListFragment : Fragment() {
    private lateinit var binding: FragmentPaymentListBinding
    private lateinit var viewModel: PaymentListViewModel
    private lateinit var manager: GridLayoutManager
    private lateinit var adapter: PaymentListAdapter
    private lateinit var tracker: SelectionTracker<Long>
    private var numCols: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentListBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).walletDao

        val cost = requireArguments().getString("cost")?.toBigDecimal() ?: "0".toBigDecimal()
        val viewModelFactory = PaymentListViewModelFactory(dataSource, cost)

        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, viewModelFactory).get(PaymentListViewModel::class.java)

        // RecyclerView stuff start
        manager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
        binding.paymentList.layoutManager = manager

        val coinValues = viewModel.walletEntity.value?.wallet?.descendingKeySet()?.toList()
        numCols = coinValues?.size ?: 0
        adapter = PaymentListAdapter(viewModel.payments, coinValues)
        binding.paymentList.adapter = adapter

        tracker = SelectionTracker.Builder<Long>(
            "paymentListSelection",
            binding.paymentList,
            StableIdKeyProvider(binding.paymentList),
            PaymentGridItemDetailsLookup(binding.paymentList),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            object : SelectionTracker.SelectionPredicate<Long>() {
                override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
                    if (key/numCols == 0L)
                        return false

                    return if (nextState)
                        tracker.selection.size() < numCols
                    else
                        true
                }

                override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
                    if (position/numCols == 0)
                        return false

                    return if (nextState)
                        tracker.selection.size() < numCols
                    else
                        true
                }

                override fun canSelectMultiple(): Boolean {
                    return true
                }
            }
        ).build()
        adapter.tracker = tracker



        // RecyclerView stuff end

        setUpObservers()
        setUpListeners()
        binding.paymentConfirmSelButton.isEnabled = false

        return binding.root
    }

    @SuppressLint("ResourceType")
    private fun setUpObservers() {
        // payments update observer
        viewModel.walletEntity.observe(viewLifecycleOwner, {
            viewModel.initializePayments()
            manager.spanCount = viewModel.walletEntity.value?.wallet?.keySet()?.size ?: 1
            adapter.coinValues = viewModel.walletEntity.value?.wallet?.descendingKeySet()?.toList()
            adapter.payments = viewModel.payments
            numCols = adapter.coinValues?.size ?: 0
        })

        adapter.tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onItemStateChanged(key: Long, selected: Boolean) {
                    super.onItemStateChanged(key, selected)
                    val row = key/numCols
                    val firstKey = (row + 1)*numCols - numCols
                    val lastKey = (row + 1)*numCols - 1

                    val keys = (firstKey..lastKey).filter { it != key }

                    tracker.setItemsSelected(keys, selected)
                    viewModel.selectPayment(row.toInt() - 1)
                    binding.paymentConfirmSelButton.isEnabled = selected
                }
            }
        )
    }

    private fun setUpListeners() {
        binding.paymentConfirmSelButton.setOnClickListener {
            val converter = WalletConverter()
            val payment = viewModel.selectedPayment.value?.let { viewModel.payments[it] }
            val bundle = bundleOf("payment" to converter.stringToWallet(payment))
            this.findNavController().navigate(R.id.action_paymentListViewFragment_to_paymentCheckFragment, bundle)
        }
    }


}