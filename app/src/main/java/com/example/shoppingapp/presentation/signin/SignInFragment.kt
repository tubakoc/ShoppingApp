package com.example.shoppingapp.presentation.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.R
import com.example.shoppingapp.common.viewBinding
import com.example.shoppingapp.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)
    private lateinit var auth : FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        auth.currentUser?.let {
            findNavController().navigate(R.id.signinToHome)
        }

        with(binding)
        {
            loginButton.setOnClickListener {
                val email = loginEmail.text.toString()
                val password = loginPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty())
                {
                    signIn(email,password)

                }

                else
                {
                    Snackbar.make(requireView(),"password must be more than 6 characters",Snackbar.LENGTH_LONG).show()
                }
            }

            signup.setOnClickListener {
                findNavController().navigate(R.id.signinToSignUp)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            findNavController().navigate(R.id.signinToHome)
        }.addOnFailureListener {
            Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
        }
    }


}