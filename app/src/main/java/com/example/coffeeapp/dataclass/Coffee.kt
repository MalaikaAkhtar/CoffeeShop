package com.example.coffeeapp.dataclass

data class Coffee(
    val id : Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val image : String
)
