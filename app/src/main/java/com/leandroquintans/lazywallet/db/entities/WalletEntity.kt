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

    //@ColumnInfo(name = "user_wallet")
    //val userWallet: Boolean = false
) {
    enum class Currency(val verboseName: String, val format: String, val currencyValues: Array<String>) {
        AMERICAN_DOLLAR(
                "American Dollar ($)",
                "%.2f",
                arrayOf(
                        "0.01",
                        "0.05",
                        "0.10",
                        "0.25",
                        "0.50",
                        "1.00",
                        "2.00",
                        "5.00",
                        "10.00",
                        "20.00",
                        "50.00",
                        "100.00"
                )
        ),
        BRITISH_POUND(
                "British Pound (£)",
                "%.2f",
                arrayOf(
                        "0.01",
                        "0.02",
                        "0.05",
                        "0.10",
                        "0.20",
                        "0.50",
                        "1.00",
                        "2.00",
                        "5.00",
                        "10.00",
                        "20.00",
                        "50.00"
                )
        ),
        EURO(
                "Euro (€)",
                "%.2f",
                arrayOf(
                        "0.01",
                        "0.02",
                        "0.05",
                        "0.10",
                        "0.20",
                        "0.50",
                        "1.00",
                        "2.00",
                        "5.00",
                        "10.00",
                        "20.00",
                        "50.00",
                        "100.00",
                        "200.00",
                        "500.00"
                )
        ),
        YEN(
                "Yen (¥)",
                "%d",
                arrayOf(
                        "1",
                        "5",
                        "10",
                        "50",
                        "100",
                        "500",
                        "1000",
                        "2000",
                        "5000",
                        "10000"
                )
        ),
    }
}