package com.example.shoppingapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.common.Resource
import com.example.shoppingapp.data.model.AddToCartRequest
import com.example.shoppingapp.data.model.BaseResponse
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(private val productsRepository: ProductsRepository): ViewModel()
{

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState


    fun getAllProducts() {
        viewModelScope.launch {

            _homeState.value = HomeState.Loading

            val result = productsRepository.getAllProducts()

            if (result is Resource.Success) {
                _homeState.value = HomeState.ProductList(result.data)
            } else if (result is Resource.Error) {
                _homeState.value = HomeState.Error(result.throwable)
            }

        }
    }

    fun getSaleProducts() {
        viewModelScope.launch {

            _homeState.value = HomeState.Loading

            val result = productsRepository.getSaleProducts()

            if (result is Resource.Success) {
                _homeState.value = HomeState.SaleProductList(result.data)
            } else if (result is Resource.Error) {
                _homeState.value = HomeState.Error(result.throwable)
            }

        }
    }

    fun addToCart(addToCartRequest: AddToCartRequest) {
        viewModelScope.launch {

            _homeState.value = HomeState.Loading

            val result = productsRepository.addToCart(addToCartRequest)

            if (result is Resource.Success) {
                _homeState.value = HomeState.PostResponse(result.data)
            } else if (result is Resource.Error) {
                _homeState.value = HomeState.Error(result.throwable)
            }

        }
    }

    fun addToFavorite(product: ProductUI) {
        viewModelScope.launch {
            productsRepository.addToFavorites(product)
        }
    }

}

sealed interface HomeState {
    object Loading : HomeState
    data class ProductList(val products: List<ProductUI>) : HomeState
    data class SaleProductList(val saleProducts: List<Products>) : HomeState
    data class PostResponse(val base: BaseResponse) : HomeState
    data class Error(val throwable: Throwable) : HomeState
}