package com.example.shoppingapp.presentation.adapters

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.common.loadImage
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.databinding.SaleProductItemBinding

class ProductSaleViewHolder(private val binding : SaleProductItemBinding,
                            private val productSaleListener: ProductSaleListener)
                           :RecyclerView.ViewHolder(binding.root){

                               fun bind(products: Products)
                               {
                                   with(binding)
                                   {
                                       tvProductTitle.text = products.title
                                       productSaleImage.loadImage(products.imageOne)

                                       if (products.saleState==true)
                                       {
                                           tvProductPrice.text = products.price.toString()
                                           tvProductPrice.setTextColor(Color.RED)
                                           tvProductSalePrice.text = products.salePrice.toString()
                                       }
                                       else
                                       {
                                           tvProductSalePrice.visibility = View.GONE
                                           tvProductPrice.text = products.price.toString()
                                       }

                                       btnAddCard.setOnClickListener {
                                           productSaleListener.onSaleProductClick(products.id!!.toInt())
                                       }

                                       root.setOnClickListener {
                                           productSaleListener.onSaleCartClick(products.id ?:1)
                                       }
                                   }
                               }
}




class SaleProducttDiffCallBack : DiffUtil.ItemCallback<Products>()
{
    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem == newItem    }

}

interface ProductSaleListener{
    fun onSaleProductClick(id:Int)

    fun onSaleCartClick(id:Int)
}