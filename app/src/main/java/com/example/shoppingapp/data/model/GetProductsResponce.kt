package com.example.shoppingapp.data.model

import com.example.shoppingapp.data.remote.Products

data class GetProductsResponce(

    val products: List<Products>?,
    val message: String?,
    val success: Int?
)