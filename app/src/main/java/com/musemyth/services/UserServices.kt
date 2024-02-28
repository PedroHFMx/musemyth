package com.musemyth.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                navController.navigate("home")
                isLoading = false
            }
            .addOnFailureListener { exception ->
                showModal = true
                println("Deu erro: " + exception.message)
                fbError = exception.message!!
            }
    }

    fun register(email: String, password: String, name: String, navController: NavController) {
        isLoading = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                FirebaseFirestore.getInstance().collection("users")
                    .document(result.user!!.uid)
                    .set(hashMapOf("name" to name, "email" to email, "id" to result.user!!.uid,
                        "accountType" to "aluno"))
                    .addOnSuccessListener {
                        navController.navigate("home")
                        isLoading = false
                    }
                    .addOnFailureListener { exception ->
                        showModal = true
                        println("Deu erro: " + exception.message)
                        fbError = exception.message!!
                    }
            }
            .addOnFailureListener { exception ->
                showModal = true
                println("Deu erro: " + exception.message)
                fbError = exception.message!!
            }
    }

    fun recoverPassword(email: String, navController: NavController){
        isLoading = true
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                navController.popBackStack()
                showModal = true
                isLoading = false
            }
            .addOnFailureListener{exception ->
                showModal = true
                println("Deu erro: " + exception.message)
                fbError = exception.message!!
            }
    }

    fun signOut(navController: NavController) {
        isLoading = true
        auth.signOut()
        navController.navigate("login")
        isLoading = false
    }
}