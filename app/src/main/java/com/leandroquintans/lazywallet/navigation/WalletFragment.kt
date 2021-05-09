package com.leandroquintans.lazywallet.navigation

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.databinding.FragmentWalletBinding
import com.leandroquintans.lazywallet.db.AppDatabase
import com.leandroquintans.lazywallet.viewmodels.WalletBaseViewModelFactory
import com.leandroquintans.lazywallet.viewmodels.WalletViewModel
import java.math.BigDecimal

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
        setUpListeners()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setUpObservers() {
        // WalletEntity LiveData observer
        viewModel.walletEntity.observe(viewLifecycleOwner, Observer {
            if (it == null)
                this.findNavController().navigate(R.id.action_walletFragment_to_walletCurrencyChooseFragmentForced)
        })
    }

    private fun setUpListeners() {
        // onClickListeners
        // Update Wallet click listener
        binding.updateWalletButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_walletFragment_to_walletCoinUpdateFragment)
        }

        // Payment click listener
        binding.paymentButton.setOnClickListener {
            if (viewModel.walletEntity.value!!.wallet.fullTotal < binding.costEditText.text.toString().toBigDecimal()) {
                val builder: AlertDialog.Builder? = activity?.let {
                    AlertDialog.Builder(it)
                }
                builder
                    ?.setMessage(R.string.paymentButtonDialogMessage)
                    ?.setTitle(R.string.paymentButtonDialogTitle)
                    ?.setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    })
                    ?.show()
            }
            else {
                val bundle = bundleOf("cost" to binding.costEditText.text.toString())
                this.findNavController()
                    .navigate(R.id.action_walletFragment_to_paymentListViewFragment, bundle)
            }
        }

        // textChangedListeners
        // costEditText textChangedListener
        binding.costEditText.addTextChangedListener { // test if it survives configuration changes
            if (it.toString().isNotEmpty()) {
                binding.paymentButton.isEnabled = it.toString().toBigDecimal() > "0".toBigDecimal()
            }
            else {
                binding.paymentButton.isEnabled = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}