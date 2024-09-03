package com.example.coffeeapp.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.dataclass.Coffee
import com.example.coffeeapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class CoffeeViewModel : ViewModel() {

    private val _hotCoffeeList = MutableLiveData<List<Coffee>>()
    val hotCoffeeList: LiveData<List<Coffee>> get() = _hotCoffeeList

    private val _coldCoffeeList = MutableLiveData<List<Coffee>>()
    val coldCoffeeList: LiveData<List<Coffee>> get() = _coldCoffeeList

    private val _filterCoffeeList = MutableLiveData<List<Coffee>>()


    init {
        getHotCoffee()
        getColdCoffee()
    }

    private fun getHotCoffee() {
        viewModelScope.launch {
            val response: Response<List<Coffee>> = RetrofitInstance.coffeeApi.getHotCoffee()
            if (response.isSuccessful) {
                response.body()?.let { coffeeList ->
                    val filteredList = coffeeList.filter { coffee ->
                        coffee.title.isNotBlank() && !coffee.title.equals("fdytdy", ignoreCase = true)
                                && !coffee.title.equals("hgvh", ignoreCase = true)
                                && !coffee.title.equals("h", ignoreCase = true)
                    }
                    _hotCoffeeList.postValue(filteredList)
                }
            } else {
                Log.d(TAG, "getHotCoffee: Error fetching hot coffee: ${response.message()}")
            }
        }
    }


    private fun getColdCoffee() {
        viewModelScope.launch {
            val response: Response<List<Coffee>> = RetrofitInstance.coffeeApi.getColdCoffee()
            if (response.isSuccessful) {
                _coldCoffeeList.postValue(response.body())
            } else {
                Log.d(TAG, "getColdCoffee: Error fetching cold coffee: ${response.message()}")
            }
        }
    }

    fun filterCoffee(query: String) {
        viewModelScope.launch {
            val hotList = hotCoffeeList.value ?: emptyList()
            val coldList = coldCoffeeList.value ?: emptyList()

            val filteredHotList = hotList.filter { it.title.contains(query, ignoreCase = true) }
            val filteredColdList = coldList.filter { it.title.contains(query, ignoreCase = true) }

            val combinedFilteredList = filteredHotList + filteredColdList
            _filterCoffeeList.value = combinedFilteredList
        }
    }

}