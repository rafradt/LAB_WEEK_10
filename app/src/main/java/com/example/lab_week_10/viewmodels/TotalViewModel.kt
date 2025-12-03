package com.example.lab_week_10

import androidx.lifecycle.*
import com.example.lab_week_10.database.TotalDao
import com.example.lab_week_10.database.Total
import kotlinx.coroutines.launch

class TotalViewModel(private val dao: TotalDao) : ViewModel() {

    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int> get() = _total

    init {
        viewModelScope.launch {
            val list = dao.getTotal(1L)
            _total.value = list.firstOrNull()?.total ?: 0
        }
    }

    fun incrementTotal() {
        viewModelScope.launch {
            val current = _total.value ?: 0
            val newTotal = current + 1

            val totalObj = Total(id = 1L, total = newTotal)
            dao.insert(totalObj)

            _total.value = newTotal
        }
    }

    fun setTotal(newTotal: Int) {
        _total.postValue(newTotal)
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
