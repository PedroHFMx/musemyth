package com.musemyth.screens

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.musemyth.components.Header
import com.musemyth.components.ShowModal
import com.musemyth.model.UserChar
import com.musemyth.model.UserStoryline
import com.musemyth.services.ContentServices
import com.musemyth.services.fbError
import com.musemyth.services.showModal
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.primary
import com.musemyth.ui.theme.secondary
import com.musemyth.ui.theme.statusBarColor
import com.musemyth.ui.theme.statusBarSecondaryColor
import com.musemyth.utils.HandleFirebaseError

var isLoadingCharacters by mutableStateOf(false)
var char by mutableStateOf(emptyList<UserChar>())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCharactersScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(statusBarColor)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val contentServices = ContentServices()

    if(showModal){
        HandleFirebaseError(fbError)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(Modifier.weight(1f)) {
                Header(
                    title = "Seus Personagens Salvos",
                    bgColor = primary,
                    navController = navController,
                    actionText = char.size.toString().padStart(2, '0') + "/10"
                )
                if (!isLoadingCharacters)
                    LazyColumn(
                        contentPadding = PaddingValues(top = 16.dp, end = 16.dp, start = 16.dp,
                            bottom = if(char.size < 10) 0.dp else 16.dp),
                        content = {
                            itemsIndexed(char) { index, charH ->
                                val isEven = index % 2 == 0
                                val fakeIndex = index + 1
                                Box(
                                    Modifier
                                        .padding(bottom = 10.dp)
                                        .fillMaxWidth()
                                        .background(
                                            if (isEven) primary else Color(0xFF2C2983),
                                            RoundedCornerShape(10.dp)
                                        )
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { }
                                ) {
                                    Row(
                                        Modifier
                                            .fillMaxSize()
                                            .padding(16.dp),
                                        Arrangement.spacedBy(16.dp),
                                        Alignment.CenterVertically
                                    ) {
                                        Box(
                                            Modifier
                                                .size(50.dp)
                                                .clip(CircleShape)
                                                .background(Color.White), Alignment.Center
                                        ) {
                                            Text(
                                                text = fakeIndex.toString().padStart(2, '0'),
                                                fontFamily = Poppins, fontSize = 14.sp
                                            )
                                        }
                                        if(charH.generatedChar?.getValue("Nome") != "")
                                            Text(
                                                modifier = Modifier.weight(1f),
                                                text = "${charH.generatedChar?.getValue("Nome")}",
                                                fontFamily = Poppins, fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        if(charH.generatedChar?.getValue("Nome") == "")
                                            LinearProgressIndicator(
                                                modifier = Modifier.weight(1f),
                                                progress = 0f,
                                                trackColor = Color.White
                                            )
                                        IconButton(onClick = {
                                            contentServices.deleteCharacter(
                                                charH.id!!,
                                                scope,
                                                snackbarHostState
                                            )
                                        }) {
                                            Icon(
                                                Icons.Rounded.Delete,
                                                "delete character",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        })
                if (isLoadingCharacters)
                    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                        CircularProgressIndicator(color = secondary)
                    }
            }



            if (char.size < 10)
                Button(
                    onClick = {
                        navController.navigate("preGenChar") {
                            popUpTo("userChar") {
                                inclusive = true
                            }
                        }
                    },
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Gerar Mais Personagens", fontSize = 14.sp)
                }
        }
    }


}
