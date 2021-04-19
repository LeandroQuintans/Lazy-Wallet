package com.leandroquintans.lazywallet.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.adapters.WalletCurrencyAdapter
import com.leandroquintans.lazywallet.databinding.FragmentWalletBinding
import com.leandroquintans.lazywallet.databinding.FragmentWalletCurrencyChooseBinding


class WalletCurrencyChooseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentWalletCurrencyChooseBinding>(inflater,
            R.layout.fragment_wallet_currency_choose,container,false)

        val adapter = WalletCurrencyAdapter()
        binding.currencyList.adapter = adapter
        return binding.root
    }
}