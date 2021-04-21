package com.leandroquintans.lazywallet.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.databinding.FragmentWalletBinding
import com.leandroquintans.lazywallet.db.AppDatabase
import com.leandroquintans.lazywallet.viewmodels.WalletViewModel
import com.leandroquintans.lazywallet.viewmodels.WalletViewModelFactory

class WalletFragment : Fragment() {
    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentWalletBinding>(inflater,
            R.layout.fragment_wallet,container,false)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).walletDao
        val viewModelFactory = WalletViewModelFactory(dataSource, application)

        Log.i("GameFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this, viewModelFactory).get(WalletViewModel::class.java)

        binding.lifecycleOwner = this
        binding.walletViewModel = viewModel

        if (viewModel.walletEntity.value == null)
            this.findNavController().navigate(R.id.action_walletFragment_to_walletCurrencyChooseFragmentForced)

        return binding.root
    }
}