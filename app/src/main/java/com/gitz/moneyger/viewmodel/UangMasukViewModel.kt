package com.gitz.moneyger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gitz.moneyger.dao.UangMasukDao
import com.gitz.moneyger.model.UangMasuk
import com.gitz.moneyger.repository.UangMasukRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UangMasukViewModel (private val repository: UangMasukRepository) : ViewModel() {

    val allUangMasuk: LiveData<List<UangMasuk>> = repository.allUangMasuk.asLiveData()

    private val _insertSuccess = MutableLiveData<Boolean>()
    val insertSuccess: LiveData<Boolean> get() = _insertSuccess

    fun resetInsertStatus() {
        _insertSuccess.value = false // Reset flag if needed
    }

    fun getUangMasukByDate(startDate: String, endDate: String): LiveData<List<UangMasuk>> {
        return repository.getUangMasukByDate(startDate, endDate).asLiveData()
    }

    fun insert(uangMasuk: UangMasuk) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.insert(uangMasuk)
                _insertSuccess.postValue(true)
            }
        }

    fun update(uangMasuk: UangMasuk) =
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.update(uangMasuk)
            }
        }

    fun delete(uangMasuk: UangMasuk) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.delete(uangMasuk)
            }
        }
}

class UangMasukViewModelFactory(private val uangMasukRepository: UangMasukRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UangMasukViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UangMasukViewModel(uangMasukRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}