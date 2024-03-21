package com.musemyth.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.musemyth.components.Header
import com.musemyth.services.ContentServices
import com.musemyth.services.characterTables
import com.musemyth.services.fbError
import com.musemyth.services.isLoading
import com.musemyth.services.showModal
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.primary
import com.musemyth.ui.theme.secondary
import com.musemyth.utils.HandleFirebaseError
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun GenerateCharacterScreen(navController: NavController? = null) {
    val contentServices = ContentServices()
    lateinit var charH: Map<Any, Any>
    val generatedChar: MutableMap<Any, Any> = mutableMapOf()

    val currentUtcDateTime = LocalDateTime.now(ZoneOffset.UTC)
    val formattedDateTime =
        currentUtcDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"))

    var telaRenderizada by remember { mutableStateOf(false) }

    if (showModal) {
        HandleFirebaseError(fbError)
    }

    // Simula um processo de renderização assíncrona
    LaunchedEffect(telaRenderizada) {
        delay(0) // Simula a renderização demorada
        telaRenderizada = true
    }

    Scaffold { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .blur(if (showModal) 20.dp else 0.dp)
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .background(Color.White)
            ) {
                Header(
                    title = "Personagem Gerado:",
                    navController = navController,
                    bgColor = primary,
                    actionText = char.size.toString().padStart(2, '0') + "/10"
                )
                val randomValuesMap by remember { mutableStateOf(mutableMapOf<String, List<*>>()) }


                if (randomValuesMap.isEmpty()) {
                    characterTables.forEach { item ->
                        val keysList = item.table!!.keys.first()
                        randomValuesMap.getOrPut(keysList) {
                            if (noGenCharItems.contains(keysList))
                                mutableListOf("")
                            else
                                (item.table.values.first() as MutableList<*>).shuffled()
                        }
                    }
                }

                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                    content = {
                        characterTables.forEach { item ->
                            val keysList = item.table!!.keys.first();
                            val valuesList: List<*> = randomValuesMap.getValue(keysList)

                            charH = mapOf(keysList to valuesList.first()!!)
                            generatedChar.putAll(charH)

                            Row(
                                Modifier.fillMaxSize(),
                                Arrangement.spacedBy(16.dp),
                                Alignment.CenterVertically
                            ) {
                                Box(
                                    Modifier
                                        .clip(CircleShape)
                                        .size(56.dp)
                                        .background(primary),
                                    Alignment.Center
                                ) {
                                    Text(
                                        keysList[0] +
                                                keysList[1].toString()
                                                    .trim()
                                                    .padEnd(1, keysList[2]),
                                        Modifier.padding(16.dp),
                                        color = Color.White,
                                        fontSize = 15.sp
                                    )
                                }
                                Column(Modifier.fillMaxSize()) {
                                    Text(
                                        text = "${keysList}:",
                                        color = primary,
                                        fontFamily = Poppins,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    if (valuesList.first() != "")
                                        Text(
                                            text = "${valuesList.first()}", fontSize = 16.sp,
                                            fontWeight = FontWeight.Light
                                        )
                                    else
                                        Divider(Modifier.padding(top = 16.dp))
                                }
                            }
                        }
                    })
            }

            Row(Modifier.padding(16.dp), Arrangement.spacedBy(4.dp)) {
                if(char.size < 10)
                Button(
                    {
                        if (telaRenderizada) {
                            navController!!.navigate("preGenChar") {
                                popUpTo("genChar") {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    Modifier
                        .weight(6f)
                        .height(50.dp),
                    enabled = !isLoading,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = secondary
                    )
                ) {
                    Text("Gerar Novamente", fontFamily = Poppins, fontSize = 15.sp)
                }
                Button(
                    {
                        if(char.size < 10){

                        contentServices.saveCharacter(
                            hashMapOf(
                                "generatedChar" to generatedChar,
                                "id" to "",
                                "time" to formattedDateTime,
                            ), navController!!
                        )
                        } else {
                            navController!!.navigate("userChar"){
                                popUpTo("genChar"){
                                    inclusive = true
                                }
                            }
                        }
                    },
                    Modifier
                        .weight(4f)
                        .height(50.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = if(char.size < 10) primary else Color.Black
                    ), enabled = !isLoading, shape = RoundedCornerShape(10.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(30.dp))
                    } else {
                        Text(
                            if (char.size < 10) "Salvar" else "Limite de 10 Personagens atingido",
                            fontFamily = Poppins, fontSize = 15.sp
                        )
                    }
                }
            }
        }

    }
}