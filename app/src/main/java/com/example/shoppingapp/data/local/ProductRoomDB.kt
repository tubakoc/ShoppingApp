package com.example.shoppingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppingapp.data.local.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductRoomDB : RoomDatabase() {

    abstract fun productsDao(): ProductDao
}