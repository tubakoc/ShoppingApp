package com.example.shoppingapp.data.mapper

import com.example.shoppingapp.data.local.ProductEntity
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.remote.Products

fun ProductUI.mapToProductEntity(): ProductEntity {
    return ProductEntity(
        category = category,
        count = count,
        description = description,
        id = id,
        imageOne = imageOne,
        price = price,
        rate = rate,
        salePrice = salePrice,
        saleState = saleState,
        title = title
    )
}

fun ProductEntity.mapToProductUI() : ProductUI {
    return ProductUI(
        category = category.orEmpty(),
        count = count ?: 1,
        description = description.orEmpty(),
        id = id ?: 1,
        imageOne = imageOne.orEmpty(),
        price = price ?: 0.0,
        rate = rate ?: 0.0,
        salePrice = salePrice ?: 0.0,
        saleState = saleState ?: false,
        title = title.orEmpty(),
        isFavorite = true
    )
}

fun Products.mapToProductUI(isFavorite: Boolean): ProductUI {
    return ProductUI(
        category = category.orEmpty(),
        count = count ?: 1,
        description = description.orEmpty(),
        id = id ?: 1,
        imageOne = imageOne.orEmpty(),
        price = price ?: 0.0,
        rate = rate ?: 0.0,
        salePrice = salePrice ?: 0.0,
        saleState = saleState ?: false,
        title = title.orEmpty(),
        isFavorite = isFavorite
    )
}