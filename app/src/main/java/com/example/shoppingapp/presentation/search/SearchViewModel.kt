package com.example.shoppingapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.common.Resource
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel  @Inject constructor(
    private val productRepository: ProductsRepository
) : ViewModel(){

    private var _state = MutableLiveData<SearchState>()
    val state: LiveData<SearchState>
        get() = _state

    private var query = ""

    fun getProductSearch(query: String) {
        viewModelScope.launch {

            _state.value = SearchState.Loading

            val result = productRepository.getSearchProducts(query)
            if (result is Resource.Success) {
                _state.value = SearchState.Data(result.data)
            } else if (result is Resource.Error) {
                _state.value = SearchState.Error(result.throwable)
            }
        }
    }


    fun addToFavorites(product: ProductUI) {
        viewModelScope.launch {
            productRepository.addToFavorites(product)
        }
    }
}

sealed interface SearchState {
    object Loading : SearchState
    data class Data(val products: List<ProductUI>) : SearchState
    data class Error(val throwable: Throwable) : SearchState
}