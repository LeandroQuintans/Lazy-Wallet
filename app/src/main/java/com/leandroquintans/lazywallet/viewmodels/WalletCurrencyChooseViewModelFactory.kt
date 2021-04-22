package com.leandroquintans.lazywallet.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leandroquintans.lazywallet.db.dao.WalletDao
import kotlin.IllegalArgumentException

class WalletCurrencyChooseViewModelFactory(private val dataSource: WalletDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletCurrencyChooseViewModel::class.java)) {
            return WalletCurrencyChooseViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}