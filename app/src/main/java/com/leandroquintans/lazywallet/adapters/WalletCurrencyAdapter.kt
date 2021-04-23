package com.leandroquintans.lazywallet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.leandroquintans.lazywallet.ButtonItemViewHolder
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.db.entities.WalletEntity
import com.leandroquintans.lazywallet.viewmodels.WalletCurrencyChooseViewModel

class WalletCurrencyAdapter(private val viewModel: WalletCurrencyChooseViewModel, private val navController: NavController): RecyclerView.Adapter<ButtonItemViewHolder>() {
    private var data = WalletEntity.Currency.values()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ButtonItemViewHolder, position: Int) {
        val item = data[position]
        holder.button.text = item.verboseName
        holder.button.setOnClickListener {
            //Log.d("WalletCurrencyAdapter", "click listener start")
            viewModel.changeCurrency(item)
            navController.navigate(R.id.action_walletCurrencyChooseFragment_to_walletFragmentForced)
            //Log.d("WalletCurrencyAdapter", "click listener end")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.currency_button_item_view, parent, false) as Button
        return ButtonItemViewHolder(view)
    }
}