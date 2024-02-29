package com.musemyth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.musemyth.components.Header
import com.musemyth.services.UserServices
import com.musemyth.ui.theme.errorColor
import com.musemyth.ui.theme.primary
import com.musemyth.ui.theme.secondary

@Composable
@Preview
fun HomeScreen(navController: NavController? = null) {

    val userServices = UserServices()

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        Arrangement.Top, Alignment.CenterHorizontally
    ) {
        Header(isHome = true)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(10.dp),
            Arrangement.Bottom,
            Alignment.CenterHorizontally
        ) {
            Column(
                Modifier
                    .weight(4f)
                    .fillMaxSize()
            ) {
                Text(text = "Seus Storylines:")
                if (false) LazyHorizontalGrid(rows = GridCells.Fixed(1), content = {

                }) else Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("Você não possui storylines", color = errorColor)
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                Modifier
                    .weight(4f)
                    .fillMaxSize()
            ) {
                Text(text = "Seus Personagens:", fontSize = 16.sp)
                if (false) LazyHorizontalGrid(rows = GridCells.Fixed(1), content = {

                }) else Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("Você não possui personagens", color = errorColor)
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                { userServices.signOut(navController!!) },
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                shape = ShapeDefaults.ExtraLarge,
                colors = ButtonDefaults.buttonColors(containerColor = secondary)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.List,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = "Gerar Storyline",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                {},
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                shape = ShapeDefaults.ExtraLarge,
                colors = ButtonDefaults.buttonColors(containerColor = primary)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        "Gerar Personagem", color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}