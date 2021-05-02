package com.leandroquintans.lazywallet.viewmodels

import androidx.lifecycle.viewModelScope
import coincost.Wallet
import com.leandroquintans.lazywallet.db.dao.WalletDao
import com.leandroquintans.lazywallet.db.entities.WalletEntity
import kotlinx.coroutines.launch

class WalletCoinUpdateViewModel(database: WalletDao) : WalletBaseViewModel(database) {
    private suspend fun updateWalletInDatabase(walletEntity: WalletEntity?) = walletEntity?.let {
        database.update(it)
    }

    fun updateCoinAmounts(wallet: Wallet) {
        viewModelScope.launch { //TODO change this to not make a new variable
            var updatedWalletEntity = _walletEntity.value
            updatedWalletEntity?.wallet = wallet
            updateWalletInDatabase(updatedWalletEntity)
        }
    }
}