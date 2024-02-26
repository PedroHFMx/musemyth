package com.example.purecompose.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.purecompose.ui.theme.poppins

@Composable
fun TitleText(text: String, align: TextAlign = TextAlign.Start, color: Color = Color.White){
    val scHeight = LocalConfiguration.current.screenHeightDp
    Text(
        text = text,
        color = color,
        fontSize = (scHeight * 0.024f).sp,
        fontWeight = FontWeight.Bold,
        fontFamily = poppins,
        textAlign = align,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun BodyText(text: String, align: TextAlign = TextAlign.Start, color: Color = Color(0xFFE9E9E9)){
    val scHeight = LocalConfiguration.current.screenHeightDp
    Text(
        text = text,
        color = color,
        fontSize = (scHeight * 0.016f).sp,
        fontWeight = FontWeight.Normal,
        fontFamily = poppins,
        textAlign = align,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun EText(text: String, align: TextAlign = TextAlign.Start, color: Color = Color(0xFFE9E9E9)){
    val scHeight = LocalConfiguration.current.screenHeightDp
    Text(
        text = text,
        color = color,
        fontSize = (scHeight * 0.02f).sp,
        fontWeight = FontWeight.Normal,
        fontFamily = poppins,
        textAlign = align,
        modifier = Modifier.fillMaxWidth(),
    )
}