package com.leandroquintans.lazywallet.db.converters

import androidx.room.TypeConverter
import com.leandroquintans.lazywallet.db.entities.WalletEntity

class CurrencyConverter {
    @TypeConverter
    fun fromInt(intCurrency: Int?): WalletEntity.Currency? {
        return intCurrency?.let { WalletEntity.Currency.values()[it] }
    }

    @TypeConverter
    fun intToCurrency(currency: WalletEntity.Currency?): Int? {
        return currency?.let { currency.ordinal }
    }
}