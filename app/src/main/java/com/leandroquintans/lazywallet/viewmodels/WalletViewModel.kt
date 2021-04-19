package com.leandroquintans.lazywallet.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel

class WalletViewModel : ViewModel() {
    init {
        Log.i("WalletViewModel", "WalletViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("WalletViewModel", "WalletViewModel destroyed!")
    }
}