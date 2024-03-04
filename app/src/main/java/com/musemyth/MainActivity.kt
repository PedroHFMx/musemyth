package com.musemyth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.musemyth.screens.GenerateStorylineScreen
import com.musemyth.screens.HomeScreen
import com.musemyth.screens.LoadingScreen
import com.musemyth.screens.LoginScreen
import com.musemyth.screens.PreGenCharScreen
import com.musemyth.screens.PreGenStoryScreen
import com.musemyth.screens.RecoverPasswordScreen
import com.musemyth.screens.RegisterScreen
import com.musemyth.screens.UserStorylinesScreen
import com.musemyth.screens.hasUser
import com.musemyth.services.fetchAllData
import com.musemyth.ui.theme.MusemythTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val user = FirebaseAuth.getInstance().currentUser
            hasUser = user != null

            if (hasUser == true) {
                fetchAllData()
            }

            val navController = rememberNavController()

            MusemythTheme(darkTheme = false) {
                NavHost(
                    navController = navController, startDestination = "loading"
                ) {
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                    composable("password") { RecoverPasswordScreen(navController) }
                    composable("loading") { LoadingScreen(navController) }
                    composable("userStory") { UserStorylinesScreen(navController) }
                    composable("preGenStory") { PreGenStoryScreen(navController) }
                    composable("preGenChar") { PreGenCharScreen(navController) }
                    composable("genStory") { GenerateStorylineScreen(navController) }
                }
            }

        }
    }
}