package com.leandroquintans.lazywallet.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coincost.Wallet
import com.leandroquintans.lazywallet.PaymentGridItemViewHolder
import com.leandroquintans.lazywallet.R
import java.math.BigDecimal

class PaymentAdapter(
    payments: List<Wallet>,
    coinValues: List<BigDecimal>?
): RecyclerView.Adapter<PaymentGridItemViewHolder>() {
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

    override fun onBindViewHolder(holder: PaymentGridItemViewHolder, position: Int) {
        val row = coinValues?.size?.let { position.div(it) } ?: 0
        val col = coinValues?.size?.let { position.rem(it) } ?: 0

        val item = if (row == 0) coinValues?.get(col) else payments[row-1][coinValues?.get(col)]
        holder.textView.text = item.toString()
        if (row == 0)
            holder.textView.typeface = Typeface.DEFAULT_BOLD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentGridItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.payment_grid_item_view, parent, false)
        return PaymentGridItemViewHolder(view)
    }
}