package com.example.purecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.purecompose.screens.HomePage
import com.example.purecompose.screens.userTappedTheme
import com.example.purecompose.ui.theme.themeSelected

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                themeSelected = if (isSystemInDarkTheme() && !userTappedTheme) "dark" else "light"
                HomePage()
        }
    }
}

