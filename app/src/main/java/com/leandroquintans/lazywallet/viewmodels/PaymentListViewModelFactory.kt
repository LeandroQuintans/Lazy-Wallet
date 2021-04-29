package com.leandroquintans.lazywallet.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leandroquintans.lazywallet.db.dao.WalletDao
import java.math.BigDecimal

class PaymentListViewModelFactory(
    private val dataSource: WalletDao,
    private val cost: BigDecimal) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(PaymentListViewModel::class.java) -> {
                return PaymentListViewModel(dataSource, cost) as T
            }
        }
        throw IllegalArgumentException("Unknown WalletBaseViewModel class")
    }
}