package com.example.shoppingapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.databinding.SearchItemBinding

class SearchAdapter(
    private val searchProductListener: SearchProductListener
) : ListAdapter<ProductUI, SearchViewHolder>(SearchDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),searchProductListener)

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bind(getItem(position))
}