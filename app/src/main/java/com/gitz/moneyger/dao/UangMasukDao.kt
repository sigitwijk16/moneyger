package com.gitz.moneyger.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gitz.moneyger.model.UangMasuk


@Dao
interface UangMasukDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(uangMasuk: UangMasuk)

    @Update
    fun update(uangMasuk: UangMasuk)

    @Delete
    fun delete(uangMasuk: UangMasuk)

    @Query("SELECT * FROM uang_masuk ORDER BY tanggal DESC")
    fun getAllUangMasuk(): LiveData<List<UangMasuk>>
}