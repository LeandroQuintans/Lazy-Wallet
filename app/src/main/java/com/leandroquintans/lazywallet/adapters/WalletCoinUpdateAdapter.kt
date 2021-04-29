package com.leandroquintans.lazywallet.adapters

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leandroquintans.lazywallet.CoinUpdateItemViewHolder
import com.leandroquintans.lazywallet.databinding.CoinUpdateItemViewBinding
import com.leandroquintans.lazywallet.db.entities.WalletEntity

class WalletCoinUpdateAdapter(walletEntity: WalletEntity?): RecyclerView.Adapter<CoinUpdateItemViewHolder>() {
    var walletEntity: WalletEntity? = walletEntity
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    
   override fun getItemCount() = walletEntity?.currency?.currencyValues?.size ?: 0

    override fun onBindViewHolder(holder: CoinUpdateItemViewHolder, position: Int) {
        val item = walletEntity?.currency?.currencyValues?.get(position)
        //Log.d("CoinUpdateAdapter", "item: $item")
        var itemAmount = walletEntity?.wallet?.get(item?.toBigDecimal()).toString()
        itemAmount = if (itemAmount == "0") "" else itemAmount
        //Log.d("CoinUpdateAdapter", "item amount: $itemAmount")
        holder.textView.text = item
        holder.editText.text = Editable.Factory.getInstance().newEditable(
            itemAmount
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinUpdateItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //val view = layoutInflater.inflate(R.layout.coin_update_item_view, parent, false)
        val binding = CoinUpdateItemViewBinding.inflate(layoutInflater, parent, false)

        return CoinUpdateItemViewHolder(binding.root)
    }
}