package com.leandroquintans.lazywallet.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import coincost.Wallet
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.adapters.PaymentAdapter
import com.leandroquintans.lazywallet.adapters.WalletCoinUpdateAdapter
import com.leandroquintans.lazywallet.databinding.FragmentPaymentListBinding
import com.leandroquintans.lazywallet.db.AppDatabase
import com.leandroquintans.lazywallet.viewmodels.PaymentListViewModel
import com.leandroquintans.lazywallet.viewmodels.PaymentListViewModelFactory
import com.leandroquintans.lazywallet.walletComparator

class PaymentListFragment : Fragment() {
    private lateinit var binding: FragmentPaymentListBinding
    private lateinit var viewModel: PaymentListViewModel
    private lateinit var manager: GridLayoutManager
    private lateinit var adapter: PaymentAdapter

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

        adapter = PaymentAdapter(viewModel.payments, viewModel.walletEntity.value?.wallet?.keySet()?.toList())
        binding.paymentList.adapter = adapter

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        // payments update observer
        viewModel.walletEntity.observe(viewLifecycleOwner, {
            viewModel.initializePayments()
            manager.spanCount = viewModel.walletEntity.value?.wallet?.keySet()?.size ?: 1
            adapter.payments = viewModel.payments.sortedWith(walletComparator)
            adapter.coinValues = viewModel.walletEntity.value?.wallet?.descendingKeySet()?.toList()
        })
    }
}