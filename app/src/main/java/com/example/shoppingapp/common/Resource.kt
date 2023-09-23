package com.example.shoppingapp.common

sealed class Resource<out T: Any>{
    data class Success<out T: Any>(val data: T) : Resource<T>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
    data class Fail(val message: String) : Resource<Nothing>()
    object Loading: Resource<Nothing>()
}
