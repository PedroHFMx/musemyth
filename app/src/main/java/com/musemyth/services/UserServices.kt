package com.musemyth.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

var isLoading by mutableStateOf(false)
var showModal by mutableStateOf(false)
var fbError by mutableStateOf("")

class UserServices {
    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser


    fun login(
        email: String, password: String, navController: NavController
    ) {
        isLoading = true
        val loginUser = auth.signInWithEmailAndPassword(email, password)
        loginUser.addOnFailureListener {
            fbError = loginUser.exception?.message!!; showModal = true; isLoading = false
        }
        loginUser.addOnSuccessListener {
            navController.navigate("home"); isLoading = false
        }

    }

    fun register(email: String, password: String, name: String) {
        isLoading = true
        val registerUser = auth.createUserWithEmailAndPassword(email, password)
        if (registerUser.isSuccessful) {
            isLoading = false
        } else {
            isLoading = false
            val e = registerUser.exception
        }
    }

    fun signOut(navController: NavController) {
        isLoading = true
        auth.signOut()
        navController.navigate("login")
    }
}