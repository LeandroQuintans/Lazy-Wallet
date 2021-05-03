package com.leandroquintans.lazywallet

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

class ButtonItemViewHolder(val button: Button): RecyclerView.ViewHolder(button)

class CoinUpdateItemViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.coinValueText)
    val editText: EditText = itemView.findViewById(R.id.coinAmountEditText)
}

class PaymentGridItemViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.paymentGridItemText)
}

class PaymentItemViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {
    val textViewCoinValue: TextView = itemView.findViewById(R.id.paymentCoinValueText)
    val textViewCoinAmount: TextView = itemView.findViewById(R.id.paymentCoinAmountText)
}