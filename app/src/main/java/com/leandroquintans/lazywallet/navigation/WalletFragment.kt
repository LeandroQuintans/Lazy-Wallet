package com.leandroquintans.lazywallet.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.databinding.FragmentWalletBinding
import com.leandroquintans.lazywallet.viewmodels.WalletViewModel

class WalletFragment : Fragment() {
    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentWalletBinding>(inflater,
            R.layout.fragment_wallet,container,false)

        Log.i("GameFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)

        return binding.root
    }
}