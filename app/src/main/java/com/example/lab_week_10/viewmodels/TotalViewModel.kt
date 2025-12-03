package com.example.lab_week_10

import androidx.lifecycle.*
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDao
import com.example.lab_week_10.database.TotalObject
import kotlinx.coroutines.launch
import java.util.*

class TotalViewModel(private val dao: TotalDao) : ViewModel() {

    private val _total = MutableLiveData<TotalObject>()
    val total: LiveData<TotalObject> get() = _total

    init {
        viewModelScope.launch {
            val list = dao.getTotal(1L)
            _total.value = list.firstOrNull()?.total ?: TotalObject(0, "")
        }
    }

    fun incrementTotal() {
        viewModelScope.launch {
            val current = _total.value?.value ?: 0
            val newTotal = current + 1
            val newDate = Date().toString()
            val totalObj = TotalObject(newTotal, newDate)
            dao.insert(Total(id = 1L, total = totalObj))
            _total.value = totalObj
        }
    }

    fun setTotal(newTotal: Int) {
        val newDate = Date().toString()
        _total.postValue(TotalObject(newTotal, newDate))
    }
}

class TotalViewModelFactory(private val dao: TotalDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TotalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TotalViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
