package com.leandroquintans.lazywallet.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import coincost.Wallet
import com.leandroquintans.lazywallet.db.dao.WalletDao
import com.leandroquintans.lazywallet.db.entities.WalletEntity
import kotlinx.coroutines.launch

class WalletViewModel(val database: WalletDao) : ViewModel() {
    private var _walletEntity = MutableLiveData<WalletEntity?>()
    val walletEntity: LiveData<WalletEntity?>
        get() = _walletEntity

    val walletAmountString = Transformations.map(walletEntity) {
        it?.currency?.formatWalletAmount(it.wallet.fullTotal)
    }

    init {
        //Log.i("WalletViewModel", "WalletViewModel created!")
        initializeWallet()
    }

    /*override fun onCleared() {
        super.onCleared()
        Log.i("WalletViewModel", "WalletViewModel destroyed!")
    }*/

    private suspend fun getWalletFromDatabase(): WalletEntity? = database.getWallet()
    /*private suspend fun deleteWalletFromDatabase() = _walletEntity.value?.let { database.deleteWallets(it) }
    private suspend fun createWalletInDatabase(currency: WalletEntity.Currency) = database.insert(
        WalletEntity(currency = currency)
    )*/

    private fun initializeWallet() {
        viewModelScope.launch {
            _walletEntity.value = getWalletFromDatabase()
        }
    }

    /*fun changeCurrency(currency: WalletEntity.Currency) {
        viewModelScope.launch {
            deleteWalletFromDatabase()
            createWalletInDatabase(currency)
            //_walletEntity.value = getWalletFromDatabase()

        }
    }*/
}