package com.example.shoppingapp.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.common.Resource
import com.example.shoppingapp.data.repository.AuthRepo
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel  @Inject constructor(private val authRepository: AuthRepo) : ViewModel()
{
    private var _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState>
        get() = _authState

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser



    fun logout() {

        viewModelScope.launch {
            authRepository.logout()
        }
    }

}

sealed interface AuthState {
    object Loading : AuthState
    data class Data(val user: FirebaseUser) : AuthState
    data class Error(val throwable: Throwable) : AuthState
}