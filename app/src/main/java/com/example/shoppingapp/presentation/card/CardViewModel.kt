package com.example.shoppingapp.presentation.card

import android.text.method.TextKeyListener.clear
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.common.Resource
import com.example.shoppingapp.data.model.BaseResponse
import com.example.shoppingapp.data.model.ClearCartRequest
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.data.repository.AuthRepository
import com.example.shoppingapp.data.repository.ProductsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel   @Inject constructor(private val productRepository: ProductsRepository,private val authRepository: AuthRepository):
    ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState>
        get() = _cartState


    val currentUser: FirebaseUser?
        get() = authRepository.currentUser




    fun getCartProducts(userId: String) {
        viewModelScope.launch {
            _cartState.value = CartState.Loading
            val result = productRepository.getCartProducts(userId)
            if (result is Resource.Success) {
                _cartState.value = CartState.CartList(result.data)
            } else if (result is Resource.Error) {
                _cartState.value = CartState.Error(result.throwable)

            }
        }
    }

    fun deleteFromCart(id: Int) {
        viewModelScope.launch {
            productRepository.deleteFromCart(id)
        }
    }

    fun clearCart(clearCartRequest: ClearCartRequest) {
        viewModelScope.launch {
            _cartState.value = CartState.Loading
            val result = productRepository.clearCart(clearCartRequest)
            if (result is Resource.Success) {
                _cartState.value = CartState.PostResponse(result.data)
            } else if (result is Resource.Error) {
                _cartState.value = CartState.Error(result.throwable)
            }
        }
    }

/*
    fun clearCart() {
        viewModelScope.launch {
            productRepository.clearCart(authRepository.getUserUid())
            getCartProducts()
        }
    }



 */
}

sealed interface CartState {
    object Loading : CartState
    data class PostResponse(val base: BaseResponse) : CartState
    data class CartList(val products: List<ProductUI>) : CartState
    data class Error(val throwable: Throwable) : CartState
}