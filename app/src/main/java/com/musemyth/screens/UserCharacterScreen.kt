package com.musemyth.screens

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
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.musemyth.model.UserChar
import com.musemyth.services.ContentServices
import com.musemyth.services.fbError
import com.musemyth.services.isLoadingCharacters
import com.musemyth.services.showModal
import com.musemyth.services.studentId
import com.musemyth.services.user
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.primary
import com.musemyth.ui.theme.secondary
import com.musemyth.ui.theme.statusBarColor
import com.musemyth.ui.theme.tertiary
import com.musemyth.utils.HandleFirebaseError

var char by mutableStateOf(emptyList<UserChar>())
var studentChar by mutableStateOf(emptyList<UserChar>())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCharactersScreen(navController: NavController) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(statusBarColor)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val contentServices = ContentServices()

    if (showModal) {
        HandleFirebaseError(fbError)
    }

    fun charPathHandle(): List<UserChar> {
        return if (user.accountType == "aluno") {
            char
        } else {
            studentChar
        }
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
                .background(primary)
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .background(tertiary)
            ) {
                Header(
                    title = if (user.accountType == "aluno") "Seus Personagens Salvos" else "Personagens salvos de\n${studentName}",
                    bgColor = primary,
                    navController = navController,
                    actionText = charPathHandle().size.toString().padStart(2, '0') + "/10"
                )
                if (!isLoadingCharacters)
                    if (charPathHandle().isEmpty())
                        Box(Modifier.fillMaxSize(), Alignment.Center) {
                            Text(text = "Nenhum Personagem salvo!")
                        }
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 16.dp, end = 16.dp, start = 16.dp,
                        bottom = if (charPathHandle().size < 10) 10.dp else 16.dp
                    ),
                    content = {
                        itemsIndexed(charPathHandle()) { index, charH ->
                            val isEven = index % 2 == 0
                            val fakeIndex = index + 1
                            Box(
                                Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth()
                                    .background(
                                        Color.White,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        charIndex = index; navController.navigate("lookChar")
                                    }
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
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isEven) primary else Color(
                                                    0xFF2C2983
                                                )
                                            ), Alignment.Center
                                    ) {
                                        Text(
                                            text = fakeIndex.toString().padStart(2, '0'),
                                            fontFamily = Poppins,
                                            fontSize = 15.sp,
                                            color = Color.White
                                        )
                                    }
                                    if (charH.generatedChar?.getValue("Nome") != "")
                                        Text(
                                            modifier = Modifier.weight(1f),
                                            text = "${charH.generatedChar?.getValue("Nome")}",
                                            fontFamily = Poppins, fontSize = 15.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.Black
                                        )
                                    if (charH.generatedChar?.getValue("Nome") == "")
                                        Divider(
                                            modifier = Modifier.weight(1f),
                                        )
                                    IconButton(onClick = {
                                        if (user.accountType == "aluno") {
                                            contentServices.deleteCharacter(
                                                charH.id!!,
                                                scope,
                                                snackbarHostState
                                            )
                                        } else {
                                            contentServices.deleteStudentCharacter(
                                                charH.id!!,
                                                studentId,
                                                scope,
                                                snackbarHostState,
                                            )
                                        }
                                    }) {
                                        Icon(
                                            Icons.Rounded.DeleteForever,
                                            "delete character",
                                            tint = if (isEven) primary else Color(0xFF2C2983)
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



            if (charPathHandle().size < 10 && user.accountType == "aluno")
                Button(
                    onClick = {
                        navController.navigate("preGenChar") {
                            popUpTo("userChar") {
                                inclusive = true
                            }
                        }
                    },
                    Modifier
                        .padding(0.dp)
                        .fillMaxWidth()
                        .height(70.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = if (charPathHandle().isEmpty()) "Gerar Personagens" else "Gerar Mais Personagens",
                        fontSize = 15.sp
                    )
                }
        }
    }


}
