package com.gitz.moneyger.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.gitz.moneyger.dao.UangMasukDao
import com.gitz.moneyger.model.UangMasuk
import kotlinx.coroutines.CoroutineScope

@Database(entities = [UangMasuk::class], exportSchema = true, version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun uangMasukDao(): UangMasukDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "moneyger_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = Migration(1, 2) {
            Log.d("Migration", "Migrating from version 1 to 2")
            it.execSQL("INSERT INTO uang_masuk (tanggal, kasir, sumber, keterangan, jumlah) VALUES ('2022-03-01', 'Kasir 1', 'Bos', 'Initial funding', 500000.0)")
            it.execSQL("INSERT INTO uang_masuk (tanggal, kasir, sumber, keterangan, jumlah) VALUES ('2022-03-02', 'Kasir 2', 'Investasi', 'Additional capital', 300000.0)")
        }

    }
}