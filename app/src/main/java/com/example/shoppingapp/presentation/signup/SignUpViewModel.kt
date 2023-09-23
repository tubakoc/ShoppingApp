package com.example.shoppingapp.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.common.Resource
import com.example.shoppingapp.data.repository.AuthRepo
import com.example.shoppingapp.presentation.signin.AuthState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val authRepository: AuthRepo) : ViewModel() {

    private var _sigUpState = MutableLiveData<AuthState>()

    val sigUpState: LiveData<AuthState>
        get() = _sigUpState

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            _sigUpState.value = AuthState.Loading

            val result = authRepository.signup(email, password)

            if (result is Resource.Success) {
                _sigUpState.value = AuthState.Data(result.data)
            } else if (result is Resource.Error) {
                _sigUpState.value = AuthState.Error(result.throwable)
            }
        }
    }

}


sealed interface SignUpState {
    object Loading : SignUpState
    data class Data(val user: FirebaseUser) : SignUpState
    data class Error(val throwable: Throwable) : SignUpState
}