package com.leandroquintans.lazywallet.db.converters

import androidx.room.TypeConverter
import coincost.Wallet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class WalletConverter {
    @TypeConverter
    fun fromString(strWallet: String?): Wallet? {
        val gson = Gson()
        val walletType = object : TypeToken<Wallet>(){}.type
        return strWallet?.let { gson.fromJson(it, walletType) }
    }

    @TypeConverter
    fun stringToWallet(wallet: Wallet?): String? {
        val gson = Gson()
        val walletType = object : TypeToken<Wallet>(){}.type
        return wallet?.let { gson.toJson(it, walletType) }
    }
}