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
        adapter = PaymentListAdapter(viewModel.payments, coinValues, viewModel)
        binding.paymentList.adapter = adapter

        tracker = SelectionTracker.Builder<Long>(
            "paymentListSelection",
            binding.paymentList,
            StableIdKeyProvider(binding.paymentList),
            PaymentGridItemDetailsLookup(binding.paymentList),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
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

        // payment selection update observer
        /*viewModel.selectedPayment.observe(viewLifecycleOwner, {
            val attrs = intArrayOf(android.R.attr.colorBackground, android.R.attr.colorFocusedHighlight)
            val ta = requireContext().theme.obtainStyledAttributes(attrs)
            val colorIntDefault = ta.getColor(0, Color.BLACK)
            val colorIntSelected = ta.getColor(1, Color.BLUE)

            for (i in numCols until binding.paymentList.childCount) {
                val view = binding.paymentList.getChildViewHolder(binding.paymentList.getChildAt(i)) as PaymentGridItemViewHolder

                view.textView.setBackgroundColor(colorIntDefault)

                binding.paymentConfirmSelButton.isEnabled = false

                if (it != null && it == i/numCols - 1) {
                    //for (j in numCols*(it+1) until numCols*(it+1)+numCols) {
                        //view = binding.paymentList.getChildViewHolder(binding.paymentList.getChildAt(j)) as PaymentGridItemViewHolder
                        view.textView.setBackgroundColor(colorIntSelected)
                    //}
                    binding.paymentConfirmSelButton.isEnabled = true
                }
            }
        })*/
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