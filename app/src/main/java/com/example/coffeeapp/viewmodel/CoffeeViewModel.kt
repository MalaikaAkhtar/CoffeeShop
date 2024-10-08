package com.example.coffeeapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.dataclass.Coffee
import com.example.coffeeapp.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class CoffeeViewModel : ViewModel() {

    private val _hotCoffeeList = MutableLiveData<List<Coffee>>(emptyList())
    val hotCoffeeList: LiveData<List<Coffee>> get() = _hotCoffeeList

    private val _coldCoffeeList = MutableLiveData<List<Coffee>>(emptyList())
    val coldCoffeeList: LiveData<List<Coffee>> get() = _coldCoffeeList

    private val _filterCoffeeList = MutableSharedFlow<List<Coffee>>()
    val filterCoffeeList: SharedFlow<List<Coffee>> get() = _filterCoffeeList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    init {
        getHotCoffee()
        getColdCoffee()
    }

    private fun getHotCoffee() {
        viewModelScope.launch {
            _isLoading.value = true
            val response: Response<List<Coffee>> = RetrofitInstance.coffeeApi.getHotCoffee()
            if (response.isSuccessful) {
                response.body()?.let { coffeeList ->
                    val filteredList = coffeeList.filter { coffee ->
                        coffee.title.isNotBlank()
                                && !coffee.title.equals("fdytdy", ignoreCase = true)
                                && !coffee.title.equals("hgvh", ignoreCase = true)
                                && !coffee.title.equals("h", ignoreCase = true)
                    }
                    _hotCoffeeList.value = filteredList
                }
            } else {
                Log.d(TAG, "getHotCoffee: Error fetching hot coffee: ${response.message()}")
            }
            _isLoading.value = false
        }
    }


    private fun getColdCoffee() {
        viewModelScope.launch {
            _isLoading.value = true
            val response: Response<List<Coffee>> = RetrofitInstance.coffeeApi.getColdCoffee()
            if (response.isSuccessful) {
                _coldCoffeeList.value = response.body() ?: emptyList()
            } else {
                Log.d(TAG, "getColdCoffee: Error fetching cold coffee: ${response.message()}")
            }
            _isLoading.value = false
        }
    }

    fun filterCoffeeList(query: String, isHotCoffee: Boolean) {
        viewModelScope.launch {
            val filteredList = if (isHotCoffee) {
                _hotCoffeeList.value?.filter { it.title.startsWith(query, ignoreCase = true) }
            } else {
                _coldCoffeeList.value?.filter { it.title.startsWith(query, ignoreCase = true) }
            }
            if (filteredList != null) {
                _filterCoffeeList.emit(filteredList)
            }
        }
    }
}
