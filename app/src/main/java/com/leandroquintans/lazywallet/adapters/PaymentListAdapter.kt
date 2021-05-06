package com.leandroquintans.lazywallet.adapters

import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import coincost.Wallet
import com.leandroquintans.lazywallet.PaymentGridItemViewHolder
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.viewmodels.PaymentListViewModel
import java.math.BigDecimal

class PaymentListAdapter(
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
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = coinValues?.size?.times(payments.size + 1) ?: 0

    override fun onBindViewHolder(holder: PaymentGridItemViewHolder, position: Int) {
        val row = coinValues?.size?.let { position.div(it) } ?: 0
        val col = coinValues?.size?.let { position.rem(it) } ?: 0

        val item = if (row == 0) coinValues?.get(col) else payments[row-1][coinValues?.get(col)]
        tracker?.let {
            val selected = it.isSelected(position.toLong())
            Log.d("PaymentListAdapter", selected.toString())

            holder.bind(item.toString(), it.isSelected(position.toLong()))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentGridItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.payment_grid_item_view, parent, false)
        return PaymentGridItemViewHolder(view)
    }
}