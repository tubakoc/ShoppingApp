package com.example.shoppingapp.data.model

import com.example.shoppingapp.data.remote.Products

data class GetProductDetail (
    val product: Products?,
    val status : Int?,
    val message : String?,
    val rate : Float?
        )