package com.leandroquintans.lazywallet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coincost.Wallet
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.TextItemViewHolder
import com.leandroquintans.lazywallet.walletComparator
import java.math.BigDecimal

class PaymentAdapter(
    payments: List<Wallet>,
    coinValues: List<BigDecimal>?
): RecyclerView.Adapter<TextItemViewHolder>() {
    var payments = payments
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var coinValues = coinValues
        set(value) {
            field = value
            notifyDataSetChanged()
        }



    override fun getItemCount(): Int = coinValues?.size?.times(payments.size + 1) ?: 0

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val row = coinValues?.size?.let { position.div(it) } ?: 0
        val col = coinValues?.size?.let { position.rem(it) } ?: 0

        val item = if (row == 0) coinValues?.get(col) else payments[row-1][coinValues?.get(col)]
        holder.textView.text = item.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }
}