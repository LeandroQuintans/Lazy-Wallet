package com.leandroquintans.lazywallet.db.entities

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import coincost.Wallet
import java.math.BigDecimal
import java.text.DecimalFormatSymbols

@Entity(tableName = "wallet_table")
data class WalletEntity(
    @PrimaryKey(autoGenerate = true)
    var walletId: Long = 0L,

    @ColumnInfo(name = "wallet")
    var wallet: Wallet = Wallet(),

    @ColumnInfo(name = "currency")
    val currency: Currency = Currency.AMERICAN_DOLLAR,

    //@ColumnInfo(name = "name")
    //val name: String = "",

//@ColumnInfo(name = "user_wallet")
//val userWallet: Boolean = false
) {
    enum class Currency(
                    val verboseName: String,
                    val format: String,
                    val regexPattern: String,
                    val currencyValues: Array<String>
            ) {
        AMERICAN_DOLLAR(
            "American Dollar ($)",
            "%.2f $",
            "((\\d+)(" + DecimalFormatSymbols.getInstance().decimalSeparator + "\\d\\d?)?)",
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
            "%.2f £",
            "((\\d+)(" + DecimalFormatSymbols.getInstance().decimalSeparator + "\\d\\d?)?)",
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
            "%.2f €",
            "((\\d+)(" + DecimalFormatSymbols.getInstance().decimalSeparator + "\\d\\d?)?)",
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
            "%.0f ¥",
            "(\\d+)",
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
        );

        fun formatWalletAmount(value: BigDecimal?): String = String.format(format, value)
    }
}