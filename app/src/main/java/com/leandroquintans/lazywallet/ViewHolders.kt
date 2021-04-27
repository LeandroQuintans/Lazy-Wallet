package com.leandroquintans.lazywallet

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.leandroquintans.lazywallet.databinding.CoinUpdateItemViewBinding

class ButtonItemViewHolder(val button: Button): RecyclerView.ViewHolder(button)

class CoinUpdateItemViewHolder(val binding: CoinUpdateItemViewBinding): RecyclerView.ViewHolder(binding.root) {
    val textView: TextView = binding.coinValueText
    val editText: EditText = binding.coinAmountEditText
}