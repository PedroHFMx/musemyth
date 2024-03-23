package com.musemyth

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.musemyth.screens.CreditsScreen
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
import com.musemyth.screens.StorylineExplanationScreen
import com.musemyth.screens.UserCharactersScreen
import com.musemyth.screens.UserStorylinesScreen
import com.musemyth.screens.hasUser
import com.musemyth.services.fetchAllData
import com.musemyth.ui.theme.MusemythTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
                        enterTransition = {
                            slideInHorizontally(animationSpec = tween(durationMillis = 400)) { fullWidth ->
                                fullWidth / 1
                            }
                        },
                        exitTransition = {
                            slideOutHorizontally(animationSpec = tween(durationMillis = 400)) { fullWidth ->
                                -fullWidth / 1
                            }
                        },
                        popEnterTransition = {
                            slideInHorizontally(animationSpec = tween(durationMillis = 400)) { fullWidth ->
                                -fullWidth / 1
                            }
                        },
                        popExitTransition = {
                            slideOutHorizontally(animationSpec = tween(durationMillis = 400)) { fullWidth ->
                                fullWidth / 1
                            }
                        },
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
                        composable("storyExplain") { StorylineExplanationScreen(navController) }
                        composable("credits") { CreditsScreen(navController) }
                        composable("lookChar") { LookCharacterScreen(navController) }
//                        composable("test/{id}", deepLinks = listOf(navDeepLink {
//                            uriPattern = "https://musemythapp.com/{id}"
//                        }), arguments = listOf(navArgument("id") {
//                            type = NavType.IntType
//                            defaultValue = 1
//                        })) { entry ->
//                            val charId = entry.arguments?.getInt("id")
//                            TestScreen(navController, charId)
//                        }
                    }
                }
            }

        }
    }
}