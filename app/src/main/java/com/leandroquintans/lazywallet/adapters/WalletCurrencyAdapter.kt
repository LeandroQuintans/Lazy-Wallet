package com.leandroquintans.lazywallet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.TextItemViewHolder
import com.leandroquintans.lazywallet.db.entities.WalletEntity

class WalletCurrencyAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data = WalletEntity.Currency.values()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.verboseName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }
}