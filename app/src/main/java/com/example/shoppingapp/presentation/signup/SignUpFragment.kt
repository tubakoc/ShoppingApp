package com.example.shoppingapp.presentation.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.R
import com.example.shoppingapp.common.viewBinding
import com.example.shoppingapp.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {


    private val binding by viewBinding (FragmentSignUpBinding::bind)

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        auth.currentUser?.let {
            // findNavController().navigate(R.id.signinToSummary)
        }

        with(binding)
        {
            signupButton.setOnClickListener {
                val email = signupEmail.text.toString()
                val password = signupPassword.text.toString()

                signUp(email,password)

            }

            txtsignin.setOnClickListener {

                findNavController().navigate(R.id.signupToSignin)
            }
        }
    }

    private fun signUp(email: String, password: String) {


        if (email != null && password != null) {
            auth.createUserWithEmailAndPassword( email, password).addOnSuccessListener {

                findNavController().navigate(R.id.signupToHome)
            }.addOnFailureListener {
                Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
                //show snackbar (it.message.orEmpty())
            }
        }
    }
}