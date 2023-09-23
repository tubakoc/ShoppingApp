package com.example.shoppingapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM favoriteproducts")
    suspend fun getFavoriteProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(product: ProductEntity)

    @Delete
    suspend fun removeFavorites(product: ProductEntity)

    @Query("SELECT title FROM favoriteproducts")
    suspend fun getFavoriteTitles(): List<String>

    @Query("SELECT id FROM favoriteproducts")
    suspend fun getFavoriteIds(): List<Int>



}