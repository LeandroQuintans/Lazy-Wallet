package com.leandroquintans.lazywallet

import android.util.Log
import coincost.Wallet

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