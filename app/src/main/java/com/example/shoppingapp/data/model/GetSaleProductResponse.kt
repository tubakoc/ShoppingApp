package com.example.shoppingapp.data.model

import com.example.shoppingapp.data.remote.Products

data class GetSaleProductResponse(
    val message: String?,
    val products: List<Products>?,
    val status: Int?
)
