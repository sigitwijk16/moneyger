package com.gitz.moneyger.repository

import com.gitz.moneyger.datasource.UangMasukLocalSource
import com.gitz.moneyger.model.UangMasuk
import kotlinx.coroutines.flow.Flow

class UangMasukRepository(private val uangMasukSource: UangMasukLocalSource) {

    val allUangMasuk: Flow<List<UangMasuk>> = uangMasukSource.getAllUangMasuk()

    fun getUangMasukByDate(startDate: String, endDate: String): Flow<List<UangMasuk>> {
        return uangMasukSource.getUangMasukByDate(startDate, endDate)
    }

    fun insert(uangMasuk: UangMasuk) {
        uangMasukSource.insert(uangMasuk)
    }

    suspend fun update(uangMasuk: UangMasuk) {
        uangMasukSource.update(uangMasuk)
    }

    fun delete(uangMasuk: UangMasuk) {
        uangMasukSource.delete(uangMasuk)
    }
}
