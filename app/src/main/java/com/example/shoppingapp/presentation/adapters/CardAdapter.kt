package com.example.shoppingapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.databinding.CardItemBinding

class CardAdapter (
    private val cardListener : CardListener,
        ):ListAdapter<ProductUI,CardViewHolder>(CardDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder  =
        CardViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent , false),cardListener)

    override fun onBindViewHolder(holder: CardViewHolder, position: Int)  = holder.bind(getItem(position))
}