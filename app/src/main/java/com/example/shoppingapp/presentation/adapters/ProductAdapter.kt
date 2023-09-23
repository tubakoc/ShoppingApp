package com.example.shoppingapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.databinding.ProductItemBinding

class ProductAdapter
    (private val productListener :ProductListener
    ) : ListAdapter<ProductUI,ProductViewHolder>(ProductDiffCallBack())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder  =
        ProductViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent , false),productListener)

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(getItem(position))
}