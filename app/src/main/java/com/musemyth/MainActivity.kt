package com.musemyth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.musemyth.screens.GenerateCharacterScreen
import com.musemyth.screens.GenerateStorylineScreen
import com.musemyth.screens.HomeScreen
import com.musemyth.screens.LoadingScreen
import com.musemyth.screens.LoginScreen
import com.musemyth.screens.LookCharacterScreen
import com.musemyth.screens.LookStorylineScreen
import com.musemyth.screens.PreGenCharScreen
import com.musemyth.screens.PreGenStoryScreen
import com.musemyth.screens.RecoverPasswordScreen
import com.musemyth.screens.RegisterScreen
import com.musemyth.screens.UserCharactersScreen
import com.musemyth.screens.UserStorylinesScreen
import com.musemyth.screens.hasUser
import com.musemyth.services.fetchAllData
import com.musemyth.ui.theme.MusemythTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val user = FirebaseAuth.getInstance().currentUser
            hasUser = user != null

            if (hasUser == true) {
                DisposableEffect(Unit) {
                    fetchAllData()
                    onDispose { }
                }
            }

            val navController = rememberNavController()

            MusemythTheme(darkTheme = false) {
                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "loading",
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = { fadeIn(tween(0)) },
                        exitTransition = { fadeOut(tween(0)) },
                        popEnterTransition = { fadeIn(tween(0)) },
                        popExitTransition = { fadeOut(tween(0)) },
                    ) {
                        composable("login") { LoginScreen(navController) }
                        composable("register") { RegisterScreen(navController) }
                        composable("home") { HomeScreen(navController) }
                        composable("password") { RecoverPasswordScreen(navController) }
                        composable("loading") { LoadingScreen(navController) }
                        composable("userStory") { UserStorylinesScreen(navController) }
                        composable("userChar") { UserCharactersScreen(navController) }
                        composable("preGenStory") { PreGenStoryScreen(navController) }
                        composable("preGenChar") { PreGenCharScreen(navController) }
                        composable("genChar") { GenerateCharacterScreen(navController) }
                        composable("genStory") { GenerateStorylineScreen(navController) }
                        composable("lookStory") { LookStorylineScreen(navController) }
                        composable("lookChar") { LookCharacterScreen(navController) }
                    }
                }
            }

        }
    }
}