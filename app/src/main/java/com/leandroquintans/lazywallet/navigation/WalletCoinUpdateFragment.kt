package com.leandroquintans.lazywallet.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coincost.Wallet
import com.leandroquintans.lazywallet.CoinUpdateItemViewHolder
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.adapters.WalletCoinUpdateAdapter
import com.leandroquintans.lazywallet.databinding.FragmentWalletCoinUpdateBinding
import com.leandroquintans.lazywallet.db.AppDatabase
import com.leandroquintans.lazywallet.viewmodels.WalletBaseViewModelFactory
import com.leandroquintans.lazywallet.viewmodels.WalletCoinUpdateViewModel
import java.lang.NumberFormatException

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

        adapter = WalletCoinUpdateAdapter(viewModel.walletEntity.value, viewModel)
        binding.coinList.adapter = adapter

        setUpObservers()
        setUpListeners()

        return binding.root
    }

    private fun setUpObservers() {
        // WalletEntity LiveData observer
        viewModel.walletEntity.observe(viewLifecycleOwner, {
            viewModel.currentWallet = it?.wallet
            adapter.walletEntity = it
        })
    }

    private fun setUpListeners() {
        // Update Coin Amounts click listener
        binding.coinUpdateButton.setOnClickListener {
            //Log.d("WalletCoinUpdateFrag", "coinUpdateButton listener start")
            viewModel.updateCoinAmounts(viewModel.currentWallet!!)
            this.findNavController().navigate(R.id.action_walletCoinUpdateFragment_to_walletFragment)
            //Log.d("WalletCoinUpdateFrag", "coinUpdateButton listener end")
        }
    }

    private fun retrieveWallet(): Wallet {
        //Log.d("WalletCoinUpdateFrag", "retrieve wallet start")
        val wallet = viewModel.walletEntity.value!!.wallet
        for (i in 0 until binding.coinList.childCount) {
            val view = binding.coinList.getChildViewHolder(binding.coinList.getChildAt(i)) as CoinUpdateItemViewHolder
            //Log.d("WalletCoinUpdateFrag", "view: $view")
            val key = view.textView.text.toString().toBigDecimal()
            val value: Int = try { view.editText.text.toString().toInt() } catch (e: NumberFormatException) { 0 }
            wallet.put(key, value)
            //Log.d("WalletCoinUpdateFrag", "wallet: $wallet")
        }
        //Log.d("WalletCoinUpdateFrag", "retrieve wallet end")
        return wallet
    }
}