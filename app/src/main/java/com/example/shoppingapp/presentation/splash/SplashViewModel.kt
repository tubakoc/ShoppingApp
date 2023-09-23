package com.example.shoppingapp.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel  @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){

    private var _state = MutableLiveData<SplashState>()
    val state: LiveData<SplashState>
        get() = _state



    fun checkUserLogin() = viewModelScope.launch {
        if (authRepository.checkUserLogin()) {
            _state.value = SplashState.GoToHome
        } else {
            _state.value = SplashState.GoToSignIn
        }
    }
}

sealed interface SplashState  {
    object GoToHome : SplashState
    object GoToSignIn : SplashState
}