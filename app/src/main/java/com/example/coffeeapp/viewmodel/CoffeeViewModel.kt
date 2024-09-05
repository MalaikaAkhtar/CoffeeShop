package com.example.coffeeapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.dataclass.Coffee
import com.example.coffeeapp.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class CoffeeViewModel : ViewModel() {

    private val _hotCoffeeList = MutableStateFlow<List<Coffee>>(emptyList())
    val hotCoffeeList: StateFlow<List<Coffee>> get() = _hotCoffeeList

    private val _coldCoffeeList = MutableStateFlow<List<Coffee>>(emptyList())
    val coldCoffeeList: StateFlow<List<Coffee>> get() = _coldCoffeeList

    private val _filterCoffeeList = MutableStateFlow<List<Coffee>>(emptyList())
    val filterCoffeeList: StateFlow<List<Coffee>> get() = _filterCoffeeList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading


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

    fun filterCoffee(query: String) {
        viewModelScope.launch {
            val hotList = hotCoffeeList.value
            val coldList = coldCoffeeList.value

            val filteredHotList = hotList.filter { it.title.contains(query, ignoreCase = true) }
            val filteredColdList = coldList.filter { it.title.contains(query, ignoreCase = true) }

            val combinedFilteredList = filteredHotList + filteredColdList
            _filterCoffeeList.value = combinedFilteredList
        }
    }

}