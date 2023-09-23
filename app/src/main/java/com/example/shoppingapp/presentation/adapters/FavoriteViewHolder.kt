package com.example.shoppingapp.presentation.adapters

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.common.loadImage
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.databinding.FavoriteItemBinding

class FavoriteViewHolder(
    private val binding: FavoriteItemBinding,
    private val favoriteListener: FavoriteListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(products: ProductUI)
    {
        with(binding)
        {
            cardImage.loadImage(products.imageOne)
            cardTitle.text= products.title

            if (products.saleState)
            {
                cardPrice.text= products.price.toString()
                cardPrice.setTextColor(Color.RED)
                cardSalePrice.text = products.salePrice.toString()
            }
            else
            {
                cardSalePrice.visibility = View.GONE
                cardPrice.text= products.price.toString()
            }

            addCard.setOnClickListener {
                favoriteListener.onAddCard(products.id)
            }

            remove.setOnClickListener {
                favoriteListener.onFavoriteDelete(products)
            }
        }
    }

}

class FavoriteDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
    override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
        return oldItem == newItem
    }

}

interface FavoriteListener {
    fun onFavoriteDelete(products: ProductUI)

    fun onAddCard(id: Int)
}

