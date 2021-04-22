package com.leandroquintans.lazywallet.viewmodels

import androidx.lifecycle.*
import com.leandroquintans.lazywallet.db.dao.WalletDao
import com.leandroquintans.lazywallet.db.entities.WalletEntity
import kotlinx.coroutines.launch

abstract class WalletBaseViewModel(val database: WalletDao) : ViewModel() {
    protected var _walletEntity = MutableLiveData<WalletEntity?>()
    val walletEntity: LiveData<WalletEntity?>
        get() = _walletEntity

    init {
        //Log.i("WalletViewModel", "WalletViewModel created!")
        initializeWallet()
    }

    /*override fun onCleared() {
        super.onCleared()
        Log.i("WalletViewModel", "WalletViewModel destroyed!")
    }*/

    private suspend fun getWalletFromDatabase(): WalletEntity? = database.getWallet()

    private fun initializeWallet() {
        viewModelScope.launch {
            _walletEntity.value = getWalletFromDatabase()
        }
    }

}