package com.leandroquintans.lazywallet.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.adapters.WalletCoinUpdateAdapter
import com.leandroquintans.lazywallet.adapters.WalletCurrencyAdapter
import com.leandroquintans.lazywallet.databinding.FragmentWalletCoinUpdateBinding
import com.leandroquintans.lazywallet.databinding.FragmentWalletCurrencyChooseBinding
import com.leandroquintans.lazywallet.db.AppDatabase
import com.leandroquintans.lazywallet.viewmodels.WalletBaseViewModelFactory
import com.leandroquintans.lazywallet.viewmodels.WalletCoinUpdateViewModel
import com.leandroquintans.lazywallet.viewmodels.WalletCurrencyChooseViewModel

class WalletCoinUpdateFragment : Fragment() {
    private lateinit var binding: FragmentWalletCoinUpdateBinding
    private lateinit var viewModel: WalletCoinUpdateViewModel
    private lateinit var adapter: WalletCoinUpdateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWalletCoinUpdateBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).walletDao
        val viewModelFactory = WalletBaseViewModelFactory(dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(WalletCoinUpdateViewModel::class.java)

        binding.lifecycleOwner = this

        Log.d("CoinUpdateFragment", "adapter attach start")
        adapter = WalletCoinUpdateAdapter(viewModel.walletEntity?.value)
        binding.coinList.adapter = adapter
        Log.d("CoinUpdateFragment", "adapter attach end")

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        // WalletEntity LiveData observer
        viewModel.walletEntity.observe(viewLifecycleOwner, {
            adapter.walletEntity = it
        })
    }
}