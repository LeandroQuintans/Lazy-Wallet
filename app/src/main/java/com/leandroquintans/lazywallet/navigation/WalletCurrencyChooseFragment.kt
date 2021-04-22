package com.leandroquintans.lazywallet.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leandroquintans.lazywallet.adapters.WalletCurrencyAdapter
import com.leandroquintans.lazywallet.databinding.FragmentWalletCurrencyChooseBinding
import com.leandroquintans.lazywallet.db.AppDatabase
import com.leandroquintans.lazywallet.viewmodels.WalletBaseViewModelFactory
import com.leandroquintans.lazywallet.viewmodels.WalletCurrencyChooseViewModel


class WalletCurrencyChooseFragment : Fragment() {
    private lateinit var binding: FragmentWalletCurrencyChooseBinding
    private lateinit var viewModel: WalletCurrencyChooseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWalletCurrencyChooseBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).walletDao
        val viewModelFactory = WalletBaseViewModelFactory(dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(WalletCurrencyChooseViewModel::class.java)

        binding.lifecycleOwner = this
        binding.walletCurrencyChooseViewModel = viewModel

        val adapter = WalletCurrencyAdapter(viewModel, this.findNavController())
        binding.currencyList.adapter = adapter

        return binding.root
    }
}