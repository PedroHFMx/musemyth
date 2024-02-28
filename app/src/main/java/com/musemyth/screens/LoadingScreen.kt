package com.musemyth.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.musemyth.ui.theme.primary

@Composable
@Preview
fun LoadingScreen(navController: NavController? = null) {

    fun handleAuth() {
        Handler(Looper.getMainLooper()).postDelayed({

            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                navController!!.navigate("home") {
                    popUpTo("loading") {
                        inclusive = true
                    }
                }

            } else {
                navController!!.navigate("login") {
                    navController.navigate("login") {
                        popUpTo("loading") {
                            inclusive = true
                        }
                    }
                }
            }
        }, 500)

    }

    LaunchedEffect(true) {
        handleAuth()
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(color = primary)
        Spacer(modifier = Modifier.padding(20.dp))
        Text(text = "Verificando Autenticação...", color = primary)
    }
}