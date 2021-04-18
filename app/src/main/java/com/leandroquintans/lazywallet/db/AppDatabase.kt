package com.leandroquintans.lazywallet.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leandroquintans.lazywallet.db.converters.CurrencyConverter
import com.leandroquintans.lazywallet.db.converters.WalletConverter
import com.leandroquintans.lazywallet.db.dao.WalletDao
import com.leandroquintans.lazywallet.db.entities.WalletEntity

@Database(
    entities = [WalletEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(WalletConverter::class, CurrencyConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val walletDao: WalletDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}