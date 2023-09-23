package com.example.shoppingapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.databinding.FavoriteItemBinding

class FavoriteAdapter ( private val favoriteListener: FavoriteListener) : ListAdapter<ProductUI, FavoriteViewHolder>(FavoriteDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent , false),favoriteListener)


    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =  holder.bind(getItem(position))
}