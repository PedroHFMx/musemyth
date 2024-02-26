package com.example.purecompose.ui.theme

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import java.util.Locale

data class COLORS(
    var primaryDark: Color =  Color(0xFF1a1a32),
    var cardColor: Color = Color(0xFF21213c),
)

var themeSelected: String by mutableStateOf("dark")

fun setColorByTheme(): COLORS {
     return when(themeSelected){
        "dark" -> COLORS()
        "light" -> COLORS(
            primaryDark = Color(0xFFECECEC),
            cardColor = Color.White
        )
         else -> COLORS()
    }

}
