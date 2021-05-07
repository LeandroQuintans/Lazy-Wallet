package com.leandroquintans.lazywallet.adapters

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import coincost.Wallet
import com.leandroquintans.lazywallet.CoinUpdateItemViewHolder
import com.leandroquintans.lazywallet.databinding.CoinUpdateItemViewBinding
import com.leandroquintans.lazywallet.db.entities.WalletEntity
import com.leandroquintans.lazywallet.viewmodels.WalletCoinUpdateViewModel

class WalletCoinUpdateAdapter(walletEntity: WalletEntity?, private val viewModel: WalletCoinUpdateViewModel ): RecyclerView.Adapter<CoinUpdateItemViewHolder>() {
    var walletEntity: WalletEntity? = walletEntity
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    
   override fun getItemCount() = walletEntity?.currency?.currencyValues?.size ?: 0

    override fun onBindViewHolder(holder: CoinUpdateItemViewHolder, position: Int) {
        val item = walletEntity?.currency?.currencyValues?.get(position)
        var itemAmount = viewModel.currentWallet?.get(item?.toBigDecimal()).toString()
        itemAmount = if (itemAmount == "0") "" else itemAmount

        holder.textView.text = item
        holder.editText.text = Editable.Factory.getInstance().newEditable(
            itemAmount
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinUpdateItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //val view = layoutInflater.inflate(R.layout.coin_update_item_view, parent, false)
        val binding = CoinUpdateItemViewBinding.inflate(layoutInflater, parent, false)

        binding.coinAmountEditText.addTextChangedListener {
            try {
                viewModel.currentWallet?.put(
                    binding.coinValueText.text.toString().toBigDecimal(),
                    binding.coinAmountEditText.text.toString().toInt()
                )
            } catch (e: NumberFormatException) {
                viewModel.currentWallet?.put(
                    binding.coinValueText.text.toString().toBigDecimal(),
                    0
                )
            }
        }

        return CoinUpdateItemViewHolder(binding.root)
    }
}