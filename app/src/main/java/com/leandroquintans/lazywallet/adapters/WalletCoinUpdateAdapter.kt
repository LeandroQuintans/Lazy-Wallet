package com.leandroquintans.lazywallet.adapters

import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.leandroquintans.lazywallet.ButtonItemViewHolder
import com.leandroquintans.lazywallet.CoinUpdateItemViewHolder
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.db.entities.WalletEntity
import com.leandroquintans.lazywallet.viewmodels.WalletCoinUpdateViewModel
import com.leandroquintans.lazywallet.viewmodels.WalletCurrencyChooseViewModel
import java.math.BigDecimal

class WalletCoinUpdateAdapter(walletEntity: WalletEntity?): RecyclerView.Adapter<CoinUpdateItemViewHolder>() {
    private var wallet = mapOf<String, Int>(Pair("1.00", 1), Pair("0.50", 2), Pair("0.20", 3)).entries.toList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var walletEntity: WalletEntity? = walletEntity
        set(value) {
            field = value
            notifyDataSetChanged()
        }


   //override fun getItemCount() = wallet.size
   override fun getItemCount() = walletEntity?.currency?.currencyValues?.size ?: 0

    override fun onBindViewHolder(holder: CoinUpdateItemViewHolder, position: Int) {
        /*val item = wallet[position]
        Log.d("CoinUpdateAdapter", "item: $item")

        holder.textView.text = item.key

        val itemAmount = item.value.toString()
        Log.d("CoinUpdateAdapter", "item amount: $itemAmount")

        holder.editText.text = Editable.Factory.getInstance().newEditable(item.value.toString())*/

        val item = walletEntity?.currency?.currencyValues?.get(position)
        Log.d("CoinUpdateAdapter", "item: $item")
        val itemAmount = walletEntity?.wallet?.get(item?.toBigDecimal()).toString()
        Log.d("CoinUpdateAdapter", "item amount: $itemAmount")
        holder.textView.text = item
        holder.editText.text = Editable.Factory.getInstance().newEditable(
            walletEntity?.wallet?.get(item?.toBigDecimal()).toString()
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinUpdateItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.coin_update_item_view, parent, false)

        return CoinUpdateItemViewHolder(view)
    }
}