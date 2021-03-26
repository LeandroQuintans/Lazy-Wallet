package com.leandroquintans.lazywallet.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.leandroquintans.lazywallet.db.entities.WalletEntity

@Dao
interface WalletDao {
    @Insert
    fun insert(wallet: WalletEntity)

    @Update
    fun update(wallet: WalletEntity)

    //@Query("SELECT * FROM wallet_table WHERE user_wallet = 1")
    @Query("SELECT * FROM wallet_table ORDER BY currency")
    fun getAllUserWallets(): LiveData<List<WalletEntity>>

    @Delete
    fun deleteWallets(vararg wallets: WalletEntity)
}