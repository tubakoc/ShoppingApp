package com.example.shoppingapp.data.repository
import com.example.shoppingapp.common.Resource
import com.example.shoppingapp.data.local.ProductDao
import com.example.shoppingapp.data.mapper.mapToProductEntity
import com.example.shoppingapp.data.mapper.mapToProductUI
import com.example.shoppingapp.data.model.AddToCartRequest
import com.example.shoppingapp.data.model.BaseResponse
import com.example.shoppingapp.data.model.ClearCartRequest
import com.example.shoppingapp.data.model.DeleteFromCartRequest
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.data.retrofit.RemoteDAOInterface
import com.google.firebase.auth.FirebaseAuth


class ProductsRepository (private val remoteDAOInterface: RemoteDAOInterface,
private val productDao: ProductDao) {

    suspend fun getProductDetail(id: Int): Resource<Products> {
        return try {
            val result = remoteDAOInterface.getProductDetail(id).product

            result?.let {
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("Product not found"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getAllProducts(): Resource<List<ProductUI>> {
        return try {
            val getFavoriteIds = getFavoriteIds()
            val result = remoteDAOInterface.getAllProducts().products.orEmpty()

            if (result.isEmpty()) {
                Resource.Error(Exception("Products not found"))
            } else {
                Resource.Success(result.map {
                    it.mapToProductUI(isFavorite = getFavoriteIds.contains(it.id))
                })
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSaleProducts(): Resource<List<Products>> {
        return try {
            val result = remoteDAOInterface.getSaleProducts().products

            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Products not found"))
            } else {
                Resource.Success(result)
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


    suspend fun getCartProducts(userId: String): Resource<List<ProductUI>> {
        return try {
            val getFavoriteIds = getFavoriteIds()
            val result = remoteDAOInterface.getCartProducts(userId)

            if (result.status == 200) {
                Resource.Success(result.products.orEmpty().map {
                    it.mapToProductUI(isFavorite = getFavoriteIds.contains(it.id))
                })
            } else {
                Resource.Error(Exception("Cart Empty"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }




/*
    suspend fun getSearchProducts(query: String): Resource<List<Products>> {
        return try {
            val result = remoteDAOInterface.getSearchProduct(query).products

            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Products not found"))
            } else {
                Resource.Success(result)
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }



 */

    suspend fun getSearchProducts(query: String): Resource<List<ProductUI>> {
        return try {
            val getFavoriteIds = getFavoriteIds()
            val result = remoteDAOInterface.getSearchProduct(query).products
            result?.let {
                Resource.Success(result.map {
                    it.mapToProductUI(isFavorite = getFavoriteIds.contains(it.id))
                })
            } ?: kotlin.run {
                Resource.Error(Exception("Products not found !"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
    suspend fun addToCart(addToCartRequest: AddToCartRequest): Resource<BaseResponse> {
        return try {
            val result = remoteDAOInterface.addToCart(addToCartRequest)
            if (result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Error(Exception("Product not added"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
    /*
    suspend fun deleteFromCart(deleteFromCartRequest: DeleteFromCartRequest): Resource<BaseResponse> {
        return try {
            val result = remoteDAOInterface.deleteFromCart(deleteFromCartRequest)
            if (result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Error(Exception("Product not deleted"))
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }



     */


    suspend fun deleteFromCart(id: Int): Resource<BaseResponse> =
        try {
            Resource.Success(remoteDAOInterface.deleteFromCart(DeleteFromCartRequest(id)))
        } catch (e: Exception) {
            Resource.Error(e)
        }



    suspend fun clearCart(clearCartRequest: ClearCartRequest): Resource<BaseResponse> {

        return try {
            val result = remoteDAOInterface.clearCart(clearCartRequest)
            if (result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Error(Exception("Cart not Cleared !"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
        getCartProducts(FirebaseAuth.getInstance().currentUser!!.uid)
    }



    suspend fun addToFavorites(product: ProductUI) {
        productDao.addToFavorites(product.mapToProductEntity())
    }

    suspend fun removeFavorites(product: ProductUI) {
        productDao.removeFavorites(product.mapToProductEntity())
    }

    suspend fun getFavoriteProducts(): Resource<List<ProductUI>> {
        return try {
            val result = productDao.getFavoriteProducts().map { it.mapToProductUI() }
            if (result.isEmpty()) {
                Resource.Error(Exception("There are no products here!"))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getFavoriteIds() = productDao.getFavoriteIds()


}