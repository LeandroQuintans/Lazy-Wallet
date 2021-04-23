package com.leandroquintans.lazywallet.adapters

import android.text.Editable
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

class WalletCoinUpdateAdapter(
    private val viewModel: WalletCoinUpdateViewModel,
    private val viewLifecycleOwner: LifecycleOwner
): RecyclerView.Adapter<CoinUpdateItemViewHolder>() {
    private var wallet = mapOf<String, Int>(Pair("1.00", 1), Pair("0.50", 2), Pair("0.20", 3)).entries.toList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

   // override fun getItemCount() = wallet?.size() ?: 0
   override fun getItemCount() = wallet.size

    override fun onBindViewHolder(holder: CoinUpdateItemViewHolder, position: Int) {
        //val item = walletValues?.get(position)
        val item = wallet[position]
        holder.textView.text = item.key
        holder.editText.text = Editable.Factory.getInstance().newEditable(item.value.toString())
        //holder.editText.text = Editable.Factory.getInstance().newEditable(
            //wallet?.get(item?.toBigDecimal()).toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinUpdateItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.coin_update_item_view, parent, false)

        /*viewModel.walletEntity.observe(viewLifecycleOwner, {
            wallet = viewModel.walletEntity.value?.wallet
            walletValues = viewModel.walletEntity.value?.currency?.currencyValues
        })*/

        return CoinUpdateItemViewHolder(view)
    }
}