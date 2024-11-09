package com.gitz.moneyger.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gitz.moneyger.database.AppDatabase
import com.gitz.moneyger.model.UangMasuk
import com.gitz.moneyger.repository.UangMasukRepository
import kotlinx.coroutines.launch

class UangMasukViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UangMasukRepository
    val allUangMasuk: LiveData<List<UangMasuk>>

    init {
        val uangMasukDao = AppDatabase.getDatabase(application).uangMasukDao()
        repository = UangMasukRepository(uangMasukDao)
        allUangMasuk = repository.allUangMasuk
    }

    fun insert(uangMasuk: UangMasuk) = viewModelScope.launch {
        repository.insert(uangMasuk)
    }

    fun update(uangMasuk: UangMasuk) = viewModelScope.launch {
        repository.update(uangMasuk)
    }

    fun delete(uangMasuk: UangMasuk) = viewModelScope.launch {
        repository.delete(uangMasuk)
    }
}
