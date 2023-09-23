package com.example.shoppingapp.presentation.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel by viewModels<SplashViewModel>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            viewModel.checkUserLogin()
        }
        initObservers()

    }


    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is SplashState.GoToHome -> {
                    findNavController().navigate(R.id.splashToHome)
                }

                SplashState.GoToSignIn -> {
                    findNavController().navigate(R.id.splashToSignIn)
                }
            }
        }
    }
}