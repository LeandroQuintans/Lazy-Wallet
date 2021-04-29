package com.leandroquintans.lazywallet.viewmodels

import androidx.lifecycle.viewModelScope
import coincost.CoinCost
import coincost.Wallet
import com.leandroquintans.lazywallet.db.dao.WalletDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal

class PaymentListViewModel(database: WalletDao, cost: BigDecimal) : WalletBaseViewModel(database) {
    private val coinCost = CoinCost(walletEntity.value!!.wallet, cost)
    private var payments = emptySet<Wallet>()

    init {
        initializePayments()
    }

    private suspend fun calculatePayments(): Set<Wallet> {
        with(Dispatchers.IO) {
            return coinCost.payments()
        }
    }

    private fun initializePayments() {
        viewModelScope.launch {
            payments = calculatePayments()
        }
    }
}