package com.leandroquintans.lazywallet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coincost.Wallet
import com.leandroquintans.lazywallet.PaymentItemViewHolder
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.db.entities.WalletEntity
import java.math.BigDecimal

class PaymentCheckAdapter(private val payment: Wallet, currency: WalletEntity.Currency?): RecyclerView.Adapter<PaymentItemViewHolder>() {
    /*private var payment = payment
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/
    private val paymentKeyList : List<BigDecimal>
        get() = payment.descendingKeySet().toList()

    var currency = currency
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = payment.size()

    override fun onBindViewHolder(holder: PaymentItemViewHolder, position: Int) {
        val coinValue = paymentKeyList[position]
        val coinAmount = payment[coinValue]

        holder.textViewCoinValue.text = currency?.formatWalletAmount(coinValue) ?: ""
        holder.textViewCoinAmount.text = coinAmount.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.payment_row_item_view, parent, false)
        return PaymentItemViewHolder(view)
    }
}