package com.musemyth.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.musemyth.model.User
import com.musemyth.model.isLoadingUser
import com.musemyth.model.user

var isLoading by mutableStateOf(false)
var showModal by mutableStateOf(false)
var fbError by mutableStateOf("")

fun fetchUserProfileData() {
    isLoadingUser = true
    FirebaseFirestore.getInstance().collection("users")
        .document(FirebaseAuth.getInstance().currentUser!!.uid)
        .addSnapshotListener { value, error ->
            isLoadingUser = false
            if (error != null) {
                return@addSnapshotListener
            }
            if (value != null) {
                user = value.toObject(User::class.java)!!
            }
        }
}

fun fetchAllData() {
    fetchStorylinesTables()
    fetchCharactersTables()
    fetchUserProfileData()

}

class UserServices {
    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser


    fun login(
        email: String, password: String, navController: NavController
    ) {
        isLoading = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                fetchAllData()
                navController.navigate("home") {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
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
                    .set(
                        hashMapOf(
                            "name" to name, "email" to email, "id" to result.user!!.uid,
                            "accountType" to "aluno"
                        )
                    )
                    .addOnSuccessListener {
                        fetchAllData()
                        navController.navigate("home") {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
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

    fun recoverPassword(email: String, navController: NavController) {
        isLoading = true
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                navController.popBackStack()
                showModal = true
                isLoading = false
            }
            .addOnFailureListener { exception ->
                showModal = true
                println("Deu erro: " + exception.message)
                fbError = exception.message!!
            }
    }

    fun signOut(navController: NavController) {
        isLoading = true
        auth.signOut()
        navController.navigate("login") {
            popUpTo(0) {
                inclusive = true
            }
        }
        isLoading = false
    }
}