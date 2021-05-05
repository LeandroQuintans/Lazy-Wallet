package com.leandroquintans.lazywallet

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import coincost.Wallet

class PaymentGridItemDetailsLookup(private val recyclerView: RecyclerView): ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as PaymentGridItemViewHolder).getItemDetails()
        }
        return null
    }
}

val walletComparator = Comparator { w1: Wallet, w2: Wallet ->
    var result = 0
    val unionKeySet = w1.keySet().union(w2.keySet()).sortedDescending()
    for (key in unionKeySet) {
        result = w2[key] - w1[key]
        if (result != 0)
            break
    }
    return@Comparator result
}