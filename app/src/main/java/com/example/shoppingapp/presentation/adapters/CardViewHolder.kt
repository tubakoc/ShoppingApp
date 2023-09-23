package com.example.shoppingapp.presentation.adapters

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.common.loadImage
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.databinding.CardItemBinding

class CardViewHolder(private val binding :CardItemBinding,
                     private val cardListener: CardListener
): RecyclerView.ViewHolder(binding.root)
{
    fun bind(product : ProductUI)
    {
        with(binding)
        {
            cardTitle.text = product.title
            cardImage.loadImage(product.imageOne)

            if(product.saleState == true)
            {
                salePrice.visibility = View.VISIBLE
                salePrice.text = "${product.salePrice} ₺"
                cardPrice.setTextColor(Color.RED)
            }

            else
            {
                salePrice.visibility = View.GONE
                cardPrice.text = "${product.price} ₺"

            }
            remove.setOnClickListener {
                cardListener.onDeleteClick(product.id ?:1)
            }

            root.setOnClickListener {
                cardListener.onCardClick(product.id ?:1)
            }
        }
    }
}


class CardDiffCallBack : DiffUtil.ItemCallback<ProductUI>()
{
    override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
        return oldItem == newItem    }

}

interface CardListener{
    fun onCardClick(id:Int)
    fun onDeleteClick(id: Int)
}