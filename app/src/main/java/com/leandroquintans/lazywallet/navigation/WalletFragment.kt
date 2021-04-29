package com.leandroquintans.lazywallet.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.databinding.FragmentWalletBinding
import com.leandroquintans.lazywallet.db.AppDatabase
import com.leandroquintans.lazywallet.viewmodels.WalletBaseViewModelFactory
import com.leandroquintans.lazywallet.viewmodels.WalletViewModel

class WalletFragment : Fragment() {
    private lateinit var binding: FragmentWalletBinding
    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWalletBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).walletDao
        val viewModelFactory = WalletBaseViewModelFactory(dataSource)

        //Log.i("GameFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this, viewModelFactory).get(WalletViewModel::class.java)

        binding.lifecycleOwner = this
        binding.walletViewModel = viewModel

        setUpObservers()
        setUpOnClickListeners()

        return binding.root
    }

    private fun setUpObservers() {
        // WalletEntity LiveData observer
        viewModel.walletEntity.observe(viewLifecycleOwner, Observer {
            if (it == null)
                this.findNavController().navigate(R.id.action_walletFragment_to_walletCurrencyChooseFragmentForced)
        })
    }

    private fun setUpOnClickListeners() {
        // Update Wallet click listener
        binding.updateWalletButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_walletFragment_to_walletCoinUpdateFragment)
        }

        // Payment click listener
        binding.paymentButton.setOnClickListener {
            val bundle = bundleOf("cost" to binding.costEditText.text.toString())
            this.findNavController().navigate(R.id.action_walletFragment_to_paymentListViewFragment, bundle)
        }
    }

}