package com.leandroquintans.lazywallet.viewmodels

import androidx.lifecycle.viewModelScope
import coincost.Wallet
import com.leandroquintans.lazywallet.db.dao.WalletDao
import kotlinx.coroutines.launch

class PaymentCheckViewModel(database: WalletDao, val payment: Wallet): WalletBaseViewModel(database) {

    fun subtractPaymentFromWallet() {
        viewModelScope.launch {
            _walletEntity.value?.wallet = _walletEntity.value?.wallet?.subtract(payment)!!
            updateWalletInDatabase(_walletEntity.value)
        }
    }
}