package com.gitz.moneyger.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gitz.moneyger.model.UangMasuk
import kotlinx.coroutines.flow.Flow

@Dao
interface UangMasukDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUangMasuk(uangMasuk: UangMasuk)

    @Update
    fun updateUangMasuk(uangMasuk: UangMasuk)

    @Delete
    fun deleteUangMasuk(uangMasuk: UangMasuk)

    @Query("SELECT * FROM uang_masuk WHERE tanggal BETWEEN :startDate AND :endDate ORDER BY tanggal ASC")
    fun getUangMasukByDate(startDate: String, endDate: String): Flow<List<UangMasuk>>

    @Query("SELECT * FROM uang_masuk ORDER BY tanggal DESC")
    fun getAllUangMasuk(): Flow<List<UangMasuk>>
}
