package com.example.shoppingapp.data.model

data class ProductUI(
    val category: String,
    val count: Int,
    val description: String,
    val id: Int,
    val imageOne: String,
    val price: Double,
    val rate: Double,
    val salePrice: Double,
    val saleState: Boolean,
    val title: String,
    val isFavorite: Boolean
)