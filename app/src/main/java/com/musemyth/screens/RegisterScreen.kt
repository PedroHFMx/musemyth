package com.musemyth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.musemyth.R
import com.musemyth.components.Header
import com.musemyth.services.UserServices
import com.musemyth.ui.theme.bgColor
import com.musemyth.ui.theme.primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun RegisterScreen(navController: NavController? = null) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val userServices = UserServices()

    Column(
        Modifier
            .fillMaxSize()
            .background(bgColor),
    ) {
        Header(title = "Cadastro", navController = navController, bgColor = primary)
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState(0))
                .padding(16.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo",
                colorFilter = ColorFilter.tint(primary), modifier = Modifier.size(200.dp, 100.dp))
            Spacer(modifier = Modifier.padding(5.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, shape = ShapeDefaults.Large),
                shape = ShapeDefaults.Large,
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Nome") },
                leadingIcon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = "") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = primary,
                )
            )
            Spacer(modifier = Modifier.padding(5.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, shape = ShapeDefaults.Large),
                shape = ShapeDefaults.Large,
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = "") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = primary,
                )
            )
            Spacer(modifier = Modifier.padding(5.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, shape = ShapeDefaults.Large),
                shape = ShapeDefaults.Large,
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Senha") },
                leadingIcon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = "") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = primary,
                )
            )
            Spacer(modifier = Modifier.padding(5.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, shape = ShapeDefaults.Large),
                shape = ShapeDefaults.Large,
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(text = "Confirmar Senha") },
                leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = primary,
                )
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(10.dp, shape = ShapeDefaults.Large), onClick = {
                        userServices.register(email, password, name)
                }) {
                Text("Cadastrar", fontSize = 16.sp)
            }
        }
    }
}