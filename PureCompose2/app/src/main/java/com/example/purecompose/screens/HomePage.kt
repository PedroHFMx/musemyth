package com.example.purecompose.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Addchart
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.purecompose.R
import com.example.purecompose.components.ActionIcon
import com.example.purecompose.components.BodyText
import com.example.purecompose.components.TitleText
import com.example.purecompose.services.ChangeLangWiget
import com.example.purecompose.services.setTHP
import com.example.purecompose.services.showBottomSheet
import com.example.purecompose.ui.theme.setColorByTheme
import com.example.purecompose.ui.theme.themeSelected


var userTappedTheme by mutableStateOf(false)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage() {

    val scHeight = LocalConfiguration.current.screenHeightDp
    val scWidth = LocalConfiguration.current.screenWidthDp

    val scrollState = rememberScrollState()
    val scrollState1 = rememberScrollState()

    val thp = setTHP()
    val tm = setColorByTheme()
    val version = "0.2.0"

    val uriHandler = LocalUriHandler.current

    Box {
        Scaffold(
            containerColor = tm.primaryDark,
            topBar = {

                Box(
                    modifier = Modifier.background(tm.primaryDark)
                ) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding((scHeight * 0.02f).dp)
                    ) {



                        ActionIcon(
                            Icons.Rounded.Menu,
                            onClick = {

                            },
                            )

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.width(IntrinsicSize.Min)
                        ) {

                            ActionIcon(Icons.Rounded.Translate, onClick = {
                                showBottomSheet = true
                            }, isTranslate = true)

                            Spacer(modifier = Modifier.padding((scWidth * .005f).dp, 0.dp))

                            ActionIcon(Icons.Rounded.WbSunny, onClick = {
                                userTappedTheme = true
                                themeSelected = if (themeSelected == "dark"){
                                    "light"
                                } else {
                                    "dark"
                                }
                            })

                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.width(IntrinsicSize.Min)
                        ) {

                            ActionIcon(Icons.Rounded.Notifications, onClick = {})

                            Spacer(modifier = Modifier.padding((scWidth * .005f).dp, 0.dp))

                            ActionIcon(Icons.Rounded.Person, onClick = {
                            })

                        }
                    }
                }
            }
        ) {

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState1)
                    .fillMaxHeight()
            ) {

                Spacer(modifier = Modifier.padding(0.dp, (scHeight * 0.06f).dp))

                Column {

                    Column(
                        modifier = Modifier.padding((scHeight * 0.02f).dp, 0.dp)
                    ) {

                        TitleText("${thp.hElabTitle}")

                        BodyText("${thp.hElabBody}")
                    }

                    Spacer(modifier = Modifier.padding(0.dp, (scHeight * .01).dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .horizontalScroll(scrollState)
                            .padding((scHeight * 0.02f).dp, 0.dp),
                    ) {

                        Card(
                            onClick = {
                                uriHandler.openUri("https://wa.me/5517991295631?text=${thp.hWapp1}")

                            },
                            elevation = CardDefaults.cardElevation((scHeight * .006).dp),
                            modifier = Modifier.size((scWidth * .65).dp, (scHeight * .32).dp),
                            shape = RoundedCornerShape((scHeight * .035).dp),
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.linearGradient(
                                            listOf(Color(0xFF1565c0), Color(0xFF1a227e))
                                        )
                                    )
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding((scHeight * .03).dp),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center,
                                ) {

                                    Image(
                                        painter = painterResource(id = R.drawable.softwarebuild),
                                        contentDescription = null,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Spacer(modifier = Modifier.padding(0.dp, (scHeight * .01).dp))

                                    TitleText("${thp.hAppCardTitle}")

                                    BodyText("${thp.hAppCardBody}")

                                }
                            }
                        }

                        Spacer(modifier = Modifier.padding((scWidth * .016).dp, 0.dp))

                        Card(
                            onClick = {uriHandler.openUri("https://wa.me/5517991295631?text=${thp.hWapp2}") },
                            modifier = Modifier.size((scWidth * .65).dp, (scHeight * .32).dp),
                            shape = RoundedCornerShape((scHeight * .035).dp),
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.linearGradient(
                                            listOf(Color(0xFF683ab7), Color(0xFF1a227e))
                                        )
                                    )
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding((scHeight * .03).dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {

                                    Image(
                                        painter = painterResource(id = R.drawable.visuidenbuild),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Spacer(modifier = Modifier.padding(0.dp, (scHeight * .01).dp))

                                    TitleText("${thp.hVisuIdenCardTitle}")

                                    BodyText("${thp.hVisuIdenCardBody}")

                                }

                            }
                        }
                    }

                    Divider(
                        modifier = Modifier.padding((scHeight * 0.015f).dp, (scHeight * .06).dp),
                        color = Color.DarkGray
                    )

                    Column(
                        modifier = Modifier.padding((scHeight * 0.015f).dp, 0.dp)
                    ) {

                        TitleText("${thp.hProjectsTitle}")

                        Spacer(modifier = Modifier.padding(0.dp, (scHeight * .01).dp))

                        Card(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            shape = RoundedCornerShape((scHeight * .035).dp),
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.linearGradient(
                                            listOf(Color(0xFF311b92), Color(0xFF0096a7))
                                        )
                                    )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding((scHeight * .03).dp)
                                ) {

                                    Column(
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(1f)
                                    ) {

                                        TitleText("${thp.hProjectsCardTitle}")

                                        Spacer(
                                            modifier = Modifier.padding(
                                                0.dp,
                                                (scHeight * .002).dp
                                            )
                                        )

                                        BodyText("${thp.hProjectsCardBody}")
                                    }

                                    Spacer(modifier = Modifier.padding((scWidth * .009).dp, 0.dp))

                                    Icon(
                                        imageVector = Icons.Rounded.Addchart,
                                        tint = Color.White,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .size((scHeight * .06).dp)
                                    )

                                }

                            }

                        }

                        Divider(
                            modifier = Modifier.padding(
                                (scHeight * 0.015f).dp,
                                (scHeight * .06).dp
                            ),
                            color = Color.DarkGray
                        )

                        BodyText("${thp.hAppVersion} $version", TextAlign.Center)

                        Spacer(modifier = Modifier.padding(0.dp, (scHeight * 0.015f).dp))
                    }
                }
            }
        }
        ChangeLangWiget()
    }


    /* Column (
         modifier = Modifier
             .fillMaxSize()
             .background(color = Color.Black)
     ){
         LazyColumn{
             itemsIndexed(foodList){ _, index ->
                 FoodItem(index)
             }
         }
     } */
}

@Composable
@Preview
fun Preview1() {
    HomePage()
}
