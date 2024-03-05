package com.musemyth.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.musemyth.components.Header
import com.musemyth.services.storylineTables
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.primary
import com.musemyth.ui.theme.secondary
import kotlinx.coroutines.delay

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun GenerateStorylineScreen(navController: NavController? = null) {
    var story: Any

    var telaRenderizada by remember { mutableStateOf(false) }

    // Simula um processo de renderização assíncrona
    LaunchedEffect(telaRenderizada) {
        delay(0) // Simula a renderização demorada
        telaRenderizada = true
    }

    Column (
        Modifier
            .fillMaxSize()
            .background(Color.White)) {
    Column(
        Modifier
            .weight(1f)
            .background(Color.White)
    ) {
        Header(title = "Storyline Gerado:", navController = navController, bgColor = secondary)
        val randomValuesMap by remember { mutableStateOf(mutableMapOf<String, List<*>>()) }


        if (randomValuesMap.isEmpty()) {
            storylineTables.forEach { item ->
                val keysList = item.table!!.keys.first()
                randomValuesMap.getOrPut(keysList) {
                    if (noGenStoryItems.contains(keysList))
                        mutableListOf("")
                    else
                        (item.table.values.first() as MutableList<*>).shuffled()
                }
            }
        }


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            contentPadding = PaddingValues(16.dp),
            content = {
                itemsIndexed(storylineTables) { index, item ->
                    val keysList = item.table!!.keys.first();
                    val valuesList: List<*> = randomValuesMap.getValue(keysList)

                    story = keysList to valuesList.first()!!

                    Row(
                        Modifier.fillMaxSize(),
                        Arrangement.spacedBy(16.dp),
                        Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .fillMaxHeight()
                                .width(57.dp)
                                .background(secondary, ShapeDefaults.ExtraLarge),
                            Alignment.Center
                        ) {
                            Text(
                                keysList[0] +
                                        keysList[1].toString()
                                            .trim()
                                            .padEnd(1, keysList[2]), Modifier.padding(16.dp),
                                color = Color.White
                            )
                        }
                        Column(Modifier.fillMaxSize()) {
                            Text(
                                text = "${keysList}:",
                                color = secondary,
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
        Row (Modifier.padding(16.dp), Arrangement.spacedBy(4.dp)) {
            Button({ if(telaRenderizada){
                navController!!.navigate("preGenStory"){
                    popUpTo("genStory"){
                        inclusive = true
                    }
                }
            } },
                Modifier
                    .weight(6f)
                    .height(50.dp),
                enabled = telaRenderizada,
                colors = ButtonDefaults.buttonColors(
                        containerColor = primary
                    )) {
                Text("Gerar Novamente", fontFamily = Poppins)
            }
            Button({ /*TODO*/ },
                Modifier
                    .weight(4f)
                    .height(50.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = secondary)) {
                Text("Salvar", fontFamily = Poppins)
            }
        }
    }
        
}