package com.leandroquintans.lazywallet.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coincost.Wallet
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.adapters.PaymentCheckAdapter
import com.leandroquintans.lazywallet.adapters.PaymentListAdapter
import com.leandroquintans.lazywallet.databinding.FragmentPaymentCheckBinding
import com.leandroquintans.lazywallet.db.AppDatabase
import com.leandroquintans.lazywallet.db.converters.WalletConverter
import com.leandroquintans.lazywallet.viewmodels.PaymentCheckViewModel
import com.leandroquintans.lazywallet.viewmodels.PaymentCheckViewModelFactory
import com.leandroquintans.lazywallet.viewmodels.PaymentListViewModel
import com.leandroquintans.lazywallet.viewmodels.PaymentListViewModelFactory
import com.leandroquintans.lazywallet.walletComparator

class PaymentCheckFragment : Fragment() {
    private lateinit var binding: FragmentPaymentCheckBinding
    private lateinit var viewModel: PaymentCheckViewModel
    private lateinit var adapter: PaymentCheckAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentCheckBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).walletDao

        val walletConverter = WalletConverter()
        val payment = walletConverter.fromString(requireArguments().getString("payment")) ?: Wallet()

        val viewModelFactory = PaymentCheckViewModelFactory(dataSource, payment)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PaymentCheckViewModel::class.java)

        binding.lifecycleOwner = this

        adapter = PaymentCheckAdapter(viewModel.payment, viewModel.walletEntity.value?.currency)
        binding.payment.adapter = adapter

        setUpObservers()
        setUpOnListeners()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.walletEntity.observe(viewLifecycleOwner, {
            adapter.currency = viewModel.walletEntity.value?.currency
        })
    }

    private fun setUpOnListeners() {
        binding.paymentCheckButton.setOnClickListener {
            viewModel.subtractPaymentFromWallet()
            this.findNavController().navigate(R.id.action_paymentCheckFragment_to_walletFragment)
        }
    }
}