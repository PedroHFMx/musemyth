@file:OptIn(ExperimentalMaterial3Api::class)

package com.musemyth.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.musemyth.R
import com.musemyth.components.Header

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun StorylineExplanationScreen(navController: NavController) {
    var scale by remember {
        mutableFloatStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    Column(Modifier.fillMaxSize()) {
        Header(title = "Explicação sobre Storylines", navController = navController)
        BoxWithConstraints(
            Modifier
                .fillMaxSize().padding(16.dp).zIndex(-1f),
            Alignment.Center
        ) {
            val state = rememberTransformableState{
                zoomChange, panChange, _ ->  scale = (scale * zoomChange).coerceIn(1f, 5f)
                val extraWidth = (scale - 1) * constraints.maxWidth
                val extraHeight = (scale - 1) * constraints.maxHeight

                val maxX = extraWidth / 2
                val maxY = extraHeight / 2

                offset = Offset(
                    x = (offset.x + panChange.x).coerceIn(-maxX, maxY),
                    y = (offset.y + panChange.y).coerceIn(-maxX, maxY),
                )

                offset += panChange
            }
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .size(8000.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }.transformable(state),
                painter = painterResource(id = R.drawable.storylines_explain),
                contentDescription = "storylines explanation"
            )

        }
    }
}