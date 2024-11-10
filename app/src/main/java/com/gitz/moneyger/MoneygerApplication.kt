package com.gitz.moneyger

import android.app.Application
import com.gitz.moneyger.database.AppDatabase
import com.gitz.moneyger.datasource.UangMasukLocalSource
import com.gitz.moneyger.repository.UangMasukRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MoneygerApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    private val uangMasukSource by lazy { UangMasukLocalSource(database.uangMasukDao()) }
    val uangMasukRepository by lazy { UangMasukRepository(uangMasukSource) }
}
