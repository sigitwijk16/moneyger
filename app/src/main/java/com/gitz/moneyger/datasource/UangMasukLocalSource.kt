package com.gitz.moneyger.datasource

import com.gitz.moneyger.dao.UangMasukDao
import com.gitz.moneyger.model.UangMasuk
import kotlinx.coroutines.flow.Flow

class UangMasukLocalSource(private val uangMasukDao: UangMasukDao) {

    fun getAllUangMasuk(): Flow<List<UangMasuk>> {
        return uangMasukDao.getAllUangMasuk()
    }

    fun getUangMasukByDate(startDate: String, endDate: String): Flow<List<UangMasuk>> {
        return uangMasukDao.getUangMasukByDate(startDate, endDate)
    }

    fun insert(uangMasuk: UangMasuk) {
        uangMasukDao.insertUangMasuk(uangMasuk)
    }

    suspend fun update(uangMasuk: UangMasuk) {
        uangMasukDao.updateUangMasuk(uangMasuk)
    }

    fun delete(uangMasuk: UangMasuk) {
        uangMasukDao.deleteUangMasuk(uangMasuk)
    }
}