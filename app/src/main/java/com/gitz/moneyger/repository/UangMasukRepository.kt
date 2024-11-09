package com.gitz.moneyger.repository
import androidx.lifecycle.LiveData
import com.gitz.moneyger.dao.UangMasukDao
import com.gitz.moneyger.model.UangMasuk

class UangMasukRepository(private val uangMasukDao: UangMasukDao) {
    val allUangMasuk: LiveData<List<UangMasuk>> = uangMasukDao.getAllUangMasuk()

    suspend fun insert(uangMasuk: UangMasuk) {
        uangMasukDao.insert(uangMasuk)
    }

    suspend fun update(uangMasuk: UangMasuk) {
        uangMasukDao.update(uangMasuk)
    }

    suspend fun delete(uangMasuk: UangMasuk) {
        uangMasukDao.delete(uangMasuk)
    }
}
