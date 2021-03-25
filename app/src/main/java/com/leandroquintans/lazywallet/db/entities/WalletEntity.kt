package com.leandroquintans.lazywallet.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import coincost.Wallet

@Entity(tableName = "wallet_table")
data class WalletEntity(
    @PrimaryKey(autoGenerate = true)
    var walletId: Long = 0L,

    @ColumnInfo(name = "wallet")
    var wallet: Wallet = Wallet(),

    @ColumnInfo(name = "currency")
    val currency: Currency = Currency.AMERICAN_DOLLAR,

    @ColumnInfo(name = "user_wallet")
    val userWallet: Boolean = false
) {
    enum class Currency {
        AMERICAN_DOLLAR,
        BRITISH_POUND,
        EURO,
        YEN,
    }
}