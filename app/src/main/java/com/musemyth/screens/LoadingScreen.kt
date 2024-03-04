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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.musemyth.ui.theme.primary

var hasUser: Boolean? by mutableStateOf(null)
@Composable
@Preview
fun LoadingScreen(navController: NavController? = null) {

    if (hasUser == null) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(color = primary)
            Spacer(modifier = Modifier.padding(20.dp))
            Text(text = "Verificando Autenticação...", color = primary)
        }
    } else {
        if (hasUser == true) {
            HomeScreen(navController)
        } else {
            LoginScreen(navController)
        }
    }

}