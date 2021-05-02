package com.leandroquintans.lazywallet.navigation

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.leandroquintans.lazywallet.PaymentGridItemViewHolder
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.adapters.PaymentListAdapter
import com.leandroquintans.lazywallet.databinding.FragmentPaymentListBinding
import com.leandroquintans.lazywallet.db.AppDatabase
import com.leandroquintans.lazywallet.db.converters.WalletConverter
import com.leandroquintans.lazywallet.viewmodels.PaymentListViewModel
import com.leandroquintans.lazywallet.viewmodels.PaymentListViewModelFactory
import com.leandroquintans.lazywallet.walletComparator

class PaymentListFragment : Fragment() {
    private lateinit var binding: FragmentPaymentListBinding
    private lateinit var viewModel: PaymentListViewModel
    private lateinit var manager: GridLayoutManager
    private lateinit var adapter: PaymentListAdapter
    private var numCols: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentListBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).walletDao

        val cost = arguments?.getString("cost")?.toBigDecimal() ?: "0".toBigDecimal()
        val viewModelFactory = PaymentListViewModelFactory(dataSource, cost)

        viewModel = ViewModelProvider(this, viewModelFactory).get(PaymentListViewModel::class.java)

        manager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
        binding.paymentList.layoutManager = manager

        val coinValues = viewModel.walletEntity.value?.wallet?.descendingKeySet()?.toList()
        numCols = coinValues?.size ?: 0
        adapter = PaymentListAdapter(viewModel.payments.sortedWith(walletComparator), coinValues, viewModel)
        binding.paymentList.adapter = adapter

        setUpObservers()
        setUpListeners()
        binding.paymentConfirmSelButton.isEnabled = false

        return binding.root
    }

    private fun setUpObservers() {
        // payments update observer
        viewModel.walletEntity.observe(viewLifecycleOwner, {
            viewModel.initializePayments()
            manager.spanCount = viewModel.walletEntity.value?.wallet?.keySet()?.size ?: 1
            adapter.payments = viewModel.payments.sortedWith(walletComparator)
            adapter.coinValues = viewModel.walletEntity.value?.wallet?.descendingKeySet()?.toList()
            numCols = adapter.coinValues?.size ?: 0
        })

        // payment selection update observer
        viewModel.selectedPayment.observe(viewLifecycleOwner, {
            for (i in numCols until binding.paymentList.childCount) {
                var view = binding.paymentList.getChildViewHolder(binding.paymentList.getChildAt(i)) as PaymentGridItemViewHolder

                val attrs = intArrayOf(android.R.attr.colorBackground, android.R.attr.colorFocusedHighlight)
                val ta = requireContext().theme.obtainStyledAttributes(attrs)
                var colorInt = ta.getColor(0, Color.BLACK)
                view.textView.setBackgroundColor(colorInt)

                binding.paymentConfirmSelButton.isEnabled = false

                if (it != null) {
                    colorInt = ta.getColor(1, Color.BLACK)
                    for (j in numCols*(it+1) until numCols*(it+1)+numCols) {
                        view = binding.paymentList.getChildViewHolder(binding.paymentList.getChildAt(j)) as PaymentGridItemViewHolder
                        view.textView.setBackgroundColor(colorInt)
                    }
                    binding.paymentConfirmSelButton.isEnabled = true
                }
            }
        })
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