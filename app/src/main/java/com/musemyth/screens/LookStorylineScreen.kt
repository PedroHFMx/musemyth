@file:OptIn(ExperimentalMaterial3Api::class)

package com.musemyth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.musemyth.R
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.secondary

var storyIndex by mutableIntStateOf(0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun LookStorylineScreen(navController: NavController? = null) {
    val fakeIndex = storyIndex + 1
    val isEven = storyIndex % 2 == 0
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(if (isEven) secondary else Color(0xFFC05AAA),)
    Scaffold(topBar = {
        Box(
            Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.common_world),
                contentDescription = "character",
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
            )
                IconButton(onClick = { navController!!.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Rounded.ChevronLeft,
                        contentDescription = "go back",
                        tint = Color.White
                    )
                }
            Box (
                Modifier
                    .fillMaxSize()
                    .padding(16.dp), Alignment.BottomEnd){
                Box (
                    Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .background(if (isEven) secondary else Color(0xFFC05AAA)),
                    Alignment.Center
                ){
                    Text(
                        text = fakeIndex.toString().padStart(2, '0'),
                        Modifier.padding(8.dp),
                        fontSize = 20.sp,
                        fontFamily = Poppins,
                        color = Color.White
                    )
                }
            }
        }
    }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            story[storyIndex].generatedStory?.forEach { storyline ->
                Row(
                    Modifier.fillMaxSize(), Arrangement.spacedBy(16.dp), Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .clip(CircleShape)
                            .size(55.dp)
                            .background(if (isEven) secondary else Color(0xFFC05AAA)),
                        Alignment.Center
                    ) {
                        Text(
                            storyline.key[0] + storyline.key[1].toString().trim().padEnd(
                                1, storyline.key[2]
                            ), Modifier.padding(16.dp), color = Color.White, fontSize = 15.sp
                        )
                    }
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        Text(text = storyline.key, color = if (isEven) secondary else Color(0xFFC05AAA),)
                        if (storyline.value != "") Text(text = "${storyline.value}")
                        else Divider(Modifier.padding(top = 16.dp))
                    }
                }
            }
        }

    }
}