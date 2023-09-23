package com.example.shoppingapp.data.model

import com.example.shoppingapp.data.remote.Products

data class GetCartProductsResponse(
    val message: String?,
    val products: List<Products>?,
    val status: Int?
)
