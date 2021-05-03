package com.leandroquintans.lazywallet.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coincost.Wallet
import com.leandroquintans.lazywallet.db.dao.WalletDao
import java.math.BigDecimal

class PaymentCheckViewModelFactory(
    private val dataSource: WalletDao,
    private val payment: Wallet) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(PaymentCheckViewModel::class.java) -> {
                return PaymentCheckViewModel(dataSource, payment) as T
            }
        }
        throw IllegalArgumentException("Unknown WalletBaseViewModel class")
    }
}