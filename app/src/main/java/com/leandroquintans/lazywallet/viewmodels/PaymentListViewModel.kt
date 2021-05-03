package com.leandroquintans.lazywallet.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import coincost.CoinCost
import coincost.Wallet
import com.leandroquintans.lazywallet.db.dao.WalletDao
import com.leandroquintans.lazywallet.walletComparator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal

class PaymentListViewModel(database: WalletDao, private val cost: BigDecimal) : WalletBaseViewModel(database) {
    var payments = emptyList<Wallet>()

    private val _selectedPayment = MutableLiveData<Int?>()
    val selectedPayment: LiveData<Int?>
        get() = _selectedPayment

    private suspend fun calculatePayments(): List<Wallet> {
        with(Dispatchers.IO) {
            val coinCost = CoinCost(walletEntity.value?.wallet, cost)
            return coinCost.payments().toList().sortedWith(walletComparator)
        }
    }

    fun initializePayments() {
        viewModelScope.launch {
            payments = calculatePayments()
        }
    }

    fun selectPayment(i: Int?) {
        _selectedPayment.value = i
    }
}