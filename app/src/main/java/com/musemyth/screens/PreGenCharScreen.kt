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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.musemyth.components.Header
import com.musemyth.services.characterTables
import com.musemyth.services.isLoadingCharacters
import com.musemyth.services.isLoadingStories
import com.musemyth.services.storylineTables
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.primary
import com.musemyth.ui.theme.secondary
import com.musemyth.ui.theme.statusBarColor
import com.musemyth.ui.theme.statusBarSecondaryColor

var noGenCharItems by mutableStateOf(emptyList<String>())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun PreGenCharScreen(navController: NavController? = null) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(statusBarColor)

    var outListS: List<String>
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier
                .weight(1f)
                .background(Color.White)
        ) {
            Header(
                title = "Escolha o que gerar:",
                bgColor = primary,
                navController = navController!!
            )
            if (isLoadingCharacters)
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator(color = Color.Black)
                }
            if (!isLoadingCharacters)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    content = {
                        itemsIndexed(
                            characterTables
                        ) { _, item ->
                            Card(
                                Modifier.clickable {
                                    if (noGenCharItems.contains(item.table!!.keys.first())) {
                                        outListS = noGenCharItems - item.table.keys.first()
                                        noGenCharItems = outListS
                                    } else {
                                        outListS = noGenCharItems + item.table.keys.first()
                                        noGenCharItems = outListS
                                    }
                                },
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxSize()
                                        .padding(16.dp), Arrangement.SpaceBetween
                                ) {
                                    Text(text = item.table!!.keys.first(), fontFamily = Poppins)
                                    Icon(
                                        imageVector =
                                        if (noGenCharItems.contains(item.table.keys.first())) Icons.Rounded.CheckBoxOutlineBlank
                                        else Icons.Rounded.CheckBox,
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    })
        }

        if (!isLoadingCharacters)
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), Alignment.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = primary)
                ) {
                    Text(text = "Gerar Personagem", fontFamily = Poppins)
                }
            }
    }
}