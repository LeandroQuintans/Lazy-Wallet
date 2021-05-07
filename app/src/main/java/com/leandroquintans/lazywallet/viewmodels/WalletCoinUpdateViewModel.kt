package com.leandroquintans.lazywallet.viewmodels

import androidx.lifecycle.viewModelScope
import coincost.Wallet
import com.leandroquintans.lazywallet.db.dao.WalletDao
import com.leandroquintans.lazywallet.db.entities.WalletEntity
import kotlinx.coroutines.launch

class WalletCoinUpdateViewModel(database: WalletDao) : WalletBaseViewModel(database) {
    var currentWallet: Wallet? = walletEntity.value?.wallet

    fun updateCoinAmounts(wallet: Wallet) {
        viewModelScope.launch { //TODO change this to not make a new variable
            _walletEntity.value?.wallet = wallet
            updateWalletInDatabase(_walletEntity.value)
        }
    }
}