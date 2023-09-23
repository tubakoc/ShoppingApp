package com.example.shoppingapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.databinding.ProductItemBinding
import com.example.shoppingapp.databinding.SaleProductItemBinding

class ProductSaleAdapter(private val productSaleListener: ProductSaleListener) : ListAdapter<Products,ProductSaleViewHolder>(SaleProducttDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSaleViewHolder  =
        ProductSaleViewHolder(SaleProductItemBinding.inflate(LayoutInflater.from(parent.context), parent , false),productSaleListener)

    override fun onBindViewHolder(holder: ProductSaleViewHolder, position: Int)  =
        holder.bind(getItem(position))
}