package com.leandroquintans.lazywallet.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import coincost.CoinCost
import coincost.Wallet
import com.leandroquintans.lazywallet.db.dao.WalletDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal

class PaymentListViewModel(database: WalletDao, private val cost: BigDecimal) : WalletBaseViewModel(database) {
    var payments = emptyList<Wallet>()

    private suspend fun calculatePayments(): Set<Wallet> {
        with(Dispatchers.IO) {
            val coinCost = CoinCost(walletEntity.value?.wallet, cost)
            return coinCost.payments().toList()
        }
    }

    fun initializePayments() {
        viewModelScope.launch {
            payments = calculatePayments()
        }
    }
}