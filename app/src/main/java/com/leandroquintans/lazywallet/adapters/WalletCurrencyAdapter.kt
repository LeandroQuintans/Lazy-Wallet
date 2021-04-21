package com.leandroquintans.lazywallet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.leandroquintans.lazywallet.ButtonItemViewHolder
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.db.entities.WalletEntity

class WalletCurrencyAdapter: RecyclerView.Adapter<ButtonItemViewHolder>() {
    var data = WalletEntity.Currency.values()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ButtonItemViewHolder, position: Int) {
        val item = data[position]
        holder.button.text = item.verboseName
        holder.button.setOnClickListener {  }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.currency_button_item_view, parent, false) as Button
        return ButtonItemViewHolder(view)
    }
}