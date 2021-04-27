package com.leandroquintans.lazywallet.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leandroquintans.lazywallet.db.dao.WalletDao
import kotlin.IllegalArgumentException

class WalletBaseViewModelFactory(private val dataSource: WalletDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(WalletViewModel::class.java) -> {
                return WalletViewModel(dataSource) as T
            }

            modelClass.isAssignableFrom(WalletCurrencyChooseViewModel::class.java) -> {
                return WalletCurrencyChooseViewModel(dataSource) as T
            }

            modelClass.isAssignableFrom(WalletCoinUpdateViewModel::class.java) -> {
                return WalletCoinUpdateViewModel(dataSource) as T
            }
        }
        throw IllegalArgumentException("Unknown WalletBaseViewModel class")
    }
}