package com.example.shoppingapp.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.common.Resource
import com.example.shoppingapp.data.model.AddToCartRequest
import com.example.shoppingapp.data.model.BaseResponse
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.repository.ProductsRepository
import com.example.shoppingapp.presentation.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(private val productsRepository: ProductsRepository) :
    ViewModel() {


    private var _favoriteState = MutableLiveData<FavoriteState>()
    val favoriteState: LiveData<FavoriteState>
        get() = _favoriteState

    fun getFavoriteProducts() {
        viewModelScope.launch {

            _favoriteState.value = FavoriteState.Loading

            val result = productsRepository.getFavoriteProducts()
            if (result is Resource.Success) {
                _favoriteState.value = FavoriteState.Data(result.data)
            } else if (result is Resource.Error) {
                _favoriteState.value = FavoriteState.Error(result.throwable)
            }
        }
    }

    fun removeFromFavorites(product: ProductUI) {
        viewModelScope.launch {
            productsRepository.removeFavorites(product)
            getFavoriteProducts()
        }
    }

    fun addToCart(addToCartRequest: AddToCartRequest) {
        viewModelScope.launch {

            _favoriteState.value = FavoriteState.Loading

            val result = productsRepository.addToCart(addToCartRequest)

            if (result is Resource.Success) {
                _favoriteState.value = FavoriteState.PostResponse(result.data as BaseResponse)
            } else if (result is Resource.Error) {
                _favoriteState.value = FavoriteState.Error(result.throwable)
            }

        }
    }

}

sealed interface FavoriteState {
    object Loading : FavoriteState
    data class Data(val products: List<ProductUI>) : FavoriteState
    data class Error(val throwable: Throwable) : FavoriteState
    data class PostResponse(val base: BaseResponse) : FavoriteState

}