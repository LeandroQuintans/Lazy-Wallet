package com.leandroquintans.lazywallet.viewmodels

import androidx.lifecycle.*
import com.leandroquintans.lazywallet.db.dao.WalletDao

class WalletViewModel(database: WalletDao) : WalletBaseViewModel(database) {
    val walletAmountString = Transformations.map(walletEntity) {
        it?.currency?.formatWalletAmount(it.wallet.fullTotal)
    }
}