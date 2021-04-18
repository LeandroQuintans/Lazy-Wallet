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
    fun insertAndGetAllWallets() {
        val walletEntity = WalletEntity()
        walletDao.insert(walletEntity)
        val walletEntities = walletDao.getAllUserWallets()

        walletEntities.observeForever { }

        assertEquals(1, walletEntities.value?.size)
        assertEquals(walletEntity.wallet, walletEntities.value?.get(0)?.wallet)
        assertEquals(walletEntity.currency, walletEntities.value?.get(0)?.currency)
        assertEquals(walletEntity.name, walletEntities.value?.get(0)?.name)
    }

}