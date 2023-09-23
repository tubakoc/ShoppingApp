package com.example.shoppingapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoppingapp.data.model.ProductUI

@Entity(tableName = "favoriteproducts")
data class ProductEntity(

    @PrimaryKey()
    @ColumnInfo("id")
    val id: Int?,

    @ColumnInfo("category")
    val category: String?,

    @ColumnInfo("count")
    val count: Int?,

    @ColumnInfo("description")
    val description: String?,

    @ColumnInfo("imageOne")
    val imageOne: String?,

    @ColumnInfo("price")
    val price: Double?,

    @ColumnInfo("rate")
    val rate: Double?,

    @ColumnInfo("salePrice")
    val salePrice: Double?,

    @ColumnInfo("saleState")
    val saleState: Boolean?,

    @ColumnInfo("title")
    val title: String?
)
