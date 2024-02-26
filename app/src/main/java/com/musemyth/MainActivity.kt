package com.musemyth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.musemyth.screens.HomeScreen
import com.musemyth.screens.LoginScreen
import com.musemyth.screens.RegisterScreen
import com.musemyth.ui.theme.MusemythTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val user = FirebaseAuth.getInstance().currentUser
            val navController = rememberNavController()

            MusemythTheme (darkTheme = false) {
                NavHost(navController = navController, startDestination = if (user == null) "login" else "home") {
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                }
            }

        }
    }
}