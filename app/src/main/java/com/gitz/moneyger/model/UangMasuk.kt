package com.gitz.moneyger.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uang_masuk")
data class UangMasuk(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tanggal: String,
    val kasir: String,
    val sumber: String,
    val keterangan: String,
    val jumlah: Double
)
