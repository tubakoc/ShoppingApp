package com.example.shoppingapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.common.loadImage
import com.example.shoppingapp.common.setStrikeThrough
import com.example.shoppingapp.common.visible
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.databinding.SearchItemBinding

class SearchViewHolder(
    private val binding: SearchItemBinding,
    private val searchProductListener: SearchProductListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: ProductUI) = with(binding) {
        tvName.text = product.title
        tvPrice.text = "${product.price} ₺"

        if (product.saleState) {
            tvSalePrice.text = "${product.salePrice} ₺"
            tvSalePrice.visible()
            tvPrice.setStrikeThrough()
        }

        if (product.isFavorite) {
            ivFavorite.setImageResource(R.drawable.ic_fav)
        } else {
            ivFavorite.setImageResource(R.drawable.ic_unfav)
        }

        ivProduct.loadImage(product.imageOne)

        root.setOnClickListener {
            searchProductListener.onProductClick(product.id)
        }

        ivFavorite.setOnClickListener {
            searchProductListener.onFavButtonClick(product)
        }
    }
}

class SearchDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
    override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
        return oldItem == newItem
    }
}

interface SearchProductListener {
    fun onProductClick(id: Int)
    fun onFavButtonClick(product: ProductUI)
}
