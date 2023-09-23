package com.example.shoppingapp.data.repository

import com.example.shoppingapp.common.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepo {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    fun checkUserLogin(): Boolean = firebaseAuth.currentUser != null

    fun getUserUid(): String = firebaseAuth.currentUser?.uid.orEmpty()

    override suspend fun signin(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        }
        catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun signup(
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().build())?.await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}


interface AuthRepo {

    val currentUser: FirebaseUser?
    suspend fun signin(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(email: String, password: String): Resource<FirebaseUser>
    fun logout()
}