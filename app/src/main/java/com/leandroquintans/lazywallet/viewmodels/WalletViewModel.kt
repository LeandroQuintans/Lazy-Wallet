package com.leandroquintans.lazywallet.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import coincost.Wallet
import com.leandroquintans.lazywallet.db.dao.WalletDao
import com.leandroquintans.lazywallet.db.entities.WalletEntity
import kotlinx.coroutines.launch

class WalletViewModel(val database: WalletDao, application: Application) : AndroidViewModel(application) {
    private var walletEntity = MutableLiveData<WalletEntity?>()
    val walletAmountString = Transformations.map(walletEntity) {
        it?.currency?.formatWalletAmount(it?.wallet?.fullTotal)
    }

    init {
        Log.i("WalletViewModel", "WalletViewModel created!")
        initializeWallet()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("WalletViewModel", "WalletViewModel destroyed!")
    }

    private suspend fun getWalletFromDatabase(): WalletEntity? = database.getWallet()

    private fun initializeWallet() {
        viewModelScope.launch {
            walletEntity.value = getWalletFromDatabase()
        }
    }
}