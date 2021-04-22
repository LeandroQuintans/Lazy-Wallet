package com.leandroquintans.lazywallet.viewmodels

import androidx.lifecycle.viewModelScope
import com.leandroquintans.lazywallet.db.dao.WalletDao
import com.leandroquintans.lazywallet.db.entities.WalletEntity
import kotlinx.coroutines.launch

class WalletCurrencyChooseViewModel(database: WalletDao) : WalletBaseViewModel(database) {

    private suspend fun deleteWalletFromDatabase() = _walletEntity.value?.let { database.deleteWallets(it) }
    private suspend fun createWalletInDatabase(currency: WalletEntity.Currency) = database.insert(
        WalletEntity(currency = currency)
    )

    fun changeCurrency(currency: WalletEntity.Currency) {
        viewModelScope.launch {
            deleteWalletFromDatabase()
            createWalletInDatabase(currency)
            //_walletEntity.value = getWalletFromDatabase()
        }
    }
}