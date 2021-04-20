package com.leandroquintans.lazywallet.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.leandroquintans.lazywallet.db.entities.WalletEntity

@Dao
interface WalletDao {
    @Insert
    suspend fun insert(wallet: WalletEntity)

    @Update
    suspend fun update(wallet: WalletEntity)

    //@Query("SELECT * FROM wallet_table WHERE user_wallet = 1")
    //@Query("SELECT * FROM wallet_table ORDER BY currency")
    //suspend fun getAllUserWallets(): LiveData<List<WalletEntity>>

    @Query("SELECT * FROM wallet_table LIMIT 1")
    suspend fun getWallet(): WalletEntity?

    @Delete
    suspend fun deleteWallets(vararg wallets: WalletEntity)
}