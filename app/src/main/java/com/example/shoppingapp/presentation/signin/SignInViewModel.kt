package com.example.shoppingapp.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.common.Resource
import com.example.shoppingapp.data.repository.AuthRepo
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(private val authRepository: AuthRepo) : ViewModel() {

    private var _sigInState = MutableLiveData<AuthState>()

    val sigInState: LiveData<AuthState>
        get() = _sigInState

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    fun signin(email: String, password: String) {
        viewModelScope.launch {
            _sigInState.value = AuthState.Loading

            val result = authRepository.signin(email, password)

            if (result is Resource.Success) {
                _sigInState.value = AuthState.Data(result.data)
            } else if (result is Resource.Error) {
                _sigInState.value = AuthState.Error(result.throwable)
            }
        }
    }

}

sealed interface SignInState {
    object Loading : SignInState
    data class Data(val user: FirebaseUser) : SignInState
    data class Error(val throwable: Throwable) : SignInState
}