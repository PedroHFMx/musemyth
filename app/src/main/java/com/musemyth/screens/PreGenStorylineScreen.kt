package com.musemyth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.musemyth.components.Header
import com.musemyth.services.iconByKey
import com.musemyth.services.isLoadingStories
import com.musemyth.services.storylineTables
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.secondary
import com.musemyth.ui.theme.statusBarSecondaryColor
import kotlinx.coroutines.delay

var noGenStoryItems by mutableStateOf(emptyList<String>())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun PreGenStoryScreen(navController: NavController? = null) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(statusBarSecondaryColor)

    var telaRenderizada by remember { mutableStateOf(false) }

    // Simula um processo de renderização assíncrona
    LaunchedEffect(telaRenderizada) {
        delay(0) // Simula a renderização demorada
        telaRenderizada = true
    }


    var outListS: List<String>

    Scaffold { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .background(Color.White)
            ) {
                Header(
                    title = "Escolha o que gerar:",
                    bgColor = secondary,
                    navController = navController!!,
                    actionPress = {
                        val keysList = arrayListOf<String>()
                        for (storyTable in storylineTables) {
                            keysList.add(storyTable.table!!.keys.first())
                        }
                        noGenStoryItems = if (noGenStoryItems.isNotEmpty()) {
                            arrayListOf()
                        } else {
                            keysList
                        }
                    },
                    actionIcon = if (noGenStoryItems.isNotEmpty()) Icons.Rounded.CheckBoxOutlineBlank
                    else Icons.Rounded.CheckBox
                )
                if (isLoadingStories) Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator(color = Color.Black)
                }
                if (!isLoadingStories) LazyColumn(verticalArrangement = Arrangement.spacedBy(0.dp),
                    content = {
                        itemsIndexed(
                            storylineTables
                        ) { _, item ->
                            Card(
                                Modifier.clickable {
                                    if (noGenStoryItems.contains(item.table!!.keys.first())) {
                                        outListS = noGenStoryItems - item.table.keys.first()
                                        noGenStoryItems = outListS
                                    } else {
                                        outListS = noGenStoryItems + item.table.keys.first()
                                        noGenStoryItems = outListS
                                    }
                                },
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    Arrangement.spacedBy(16.dp),
                                    Alignment.CenterVertically
                                ) {
                                    Row(
                                        Modifier.weight(1f),
                                        Arrangement.spacedBy(16.dp),
                                        Alignment.CenterVertically
                                    ) {
                                        Box(
                                            Modifier
                                                .clip(CircleShape)
                                                .size(56.dp)
                                                .background(secondary), Alignment.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(id = iconByKey(item.table!!.keys.first())),
                                                contentDescription = "storyline icon",
                                                Modifier.padding(10.dp),
                                                tint = Color.White
                                            )
//                                                Text(
//                                                    item.table!!.keys.first()[0] +
//                                                            item.table.keys.first()[1].toString()
//                                                                .trim()
//                                                                .padEnd(
//                                                                    1,
//                                                                    item.table.keys.first()[2]
//                                                                ),
//                                                    Modifier.padding(16.dp),
//                                                    color = Color.White,
//                                                    fontSize = 15.sp
//                                                )
                                        }
                                        Text(
                                            text = item.table!!.keys.first(),
                                            fontFamily = Poppins,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                    Icon(
                                        imageVector = if (noGenStoryItems.contains(item.table!!.keys.first())) Icons.Rounded.CheckBoxOutlineBlank
                                        else Icons.Rounded.CheckBox,
                                        contentDescription = "check storyline item"
                                    )
                                }
                            }
                        }
                    })
            }

            if (!isLoadingStories) Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), Alignment.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = telaRenderizada,
                    onClick = {
                        if (telaRenderizada) {
                            navController!!.navigate("genStory") {
                                popUpTo("preGenStory") {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = secondary)
                ) {
                    Text(text = "Gerar Storyline", fontFamily = Poppins, fontSize = 15.sp)
                }
            }
        }

    }
}