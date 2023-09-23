package com.example.shoppingapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.common.loadImage
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.databinding.ProductItemBinding

class ProductViewHolder (private val binding : ProductItemBinding,
                         private val productListener: ProductListener
                         ):RecyclerView.ViewHolder(binding.root)
{
                             fun bind(product : ProductUI){

                                 with(binding)
                                 {
                                     productsTitle.text = product.title
                                     bookPriceText.text = "${product.price} ₺"
                                     productsImageView.loadImage(product.imageOne)

                                     root.setOnClickListener {
                                         productListener.onProductClick(product.id)
                                     }

                                     addFav.setOnClickListener{
                                         productListener.onAddFavorite(product)
                                     }
                                 }


                             }
}


class ProductDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
    override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
        return oldItem.id == newItem.id
    }
}
interface ProductListener{
    fun onProductClick(id:Int)
    fun onAddFavorite(product : ProductUI)
}

