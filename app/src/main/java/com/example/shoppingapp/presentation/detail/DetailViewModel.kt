package com.example.shoppingapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.common.Resource
import com.example.shoppingapp.data.model.AddToCartRequest
import com.example.shoppingapp.data.model.BaseResponse
import com.example.shoppingapp.data.remote.Products
import com.example.shoppingapp.data.repository.AuthRepo
import com.example.shoppingapp.data.repository.ProductsRepository
import com.example.shoppingapp.presentation.home.HomeState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val productsRepository: ProductsRepository,private val authRepository: AuthRepo) : ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState>
        get() = _detailState


    val currentUser: FirebaseUser?
        get() = authRepository.currentUser



    fun getProductDetail(id: Int) {
        viewModelScope.launch {
            _detailState.value = DetailState.Loading
            val result = productsRepository.getProductDetail(id)
            if (result is Resource.Success) {
                _detailState.value = DetailState.Data(result.data)
            } else if (result is Resource.Error) {
                _detailState.value = DetailState.Error(result.throwable)
            }
        }
    }

    fun addToCart(addToCartRequest: AddToCartRequest) {
        viewModelScope.launch {

            _detailState.value = DetailState.Loading

            val result = productsRepository.addToCart(addToCartRequest)

            if (result is Resource.Success) {
                _detailState.value = DetailState.PostResponse(result.data as BaseResponse)
            } else if (result is Resource.Error) {
                _detailState.value = DetailState.Error(result.throwable)
            }

        }
    }


}

sealed interface DetailState {
    object Loading : DetailState
    data class Data(val product: Products) : DetailState
    data class Error(val throwable: Throwable) : DetailState
    data class PostResponse(val base: BaseResponse) : DetailState


}