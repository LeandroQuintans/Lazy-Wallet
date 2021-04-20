package com.leandroquintans.lazywallet

import androidx.annotation.UiThread
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.leandroquintans.lazywallet.db.AppDatabase
import com.leandroquintans.lazywallet.db.dao.WalletDao
import com.leandroquintans.lazywallet.db.entities.WalletEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var walletDao: WalletDao
    private lateinit var db: AppDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        walletDao = db.walletDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWallet() {
        val walletEntity = WalletEntity()
        var walletEntityDB : WalletEntity?
        runBlocking {
            walletDao.insert(walletEntity)
            walletEntityDB = walletDao.getWallet()
        }

        //assertEquals(1, walletEntity.value?.size)
        assertEquals(walletEntity.wallet, walletEntityDB?.wallet)
        assertEquals(walletEntity.currency, walletEntityDB?.currency)
        //assertEquals(walletEntity.name, walletEntity.value?.name)
    }

}