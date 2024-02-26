package com.musemyth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.musemyth.R
import com.musemyth.components.ShowModal
import com.musemyth.services.UserServices
import com.musemyth.services.fbError
import com.musemyth.services.isLoading
import com.musemyth.services.showModal
import com.musemyth.ui.theme.bgColor
import com.musemyth.ui.theme.errorColor
import com.musemyth.ui.theme.primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun LoginScreen(navController: NavController? = null) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }

    val regexEmail = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+\$")

    val userServices = UserServices()

    fun handleLogin() {
        if (regexEmail.matches(email) && password.length > 7) {
            userServices.login(email, password, navController!!)
        }
    }

    fun handleErrors(emailI: String? = null, passwordI: String? = null) {
        if (emailI != null) {
            emailError = !regexEmail.matches(emailI.trim())
        }
        if(passwordI != null){
            passwordError = passwordI.length < 8
        }
    }

    if (showModal) {
        ShowModal("Ops!", fbError, { showModal = false })
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(24.dp), Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                colorFilter = ColorFilter.tint(primary),
                modifier = Modifier
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(2.dp, shape = ShapeDefaults.ExtraLarge),
                enabled = !isLoading,
                shape = ShapeDefaults.ExtraLarge,
                value = email,
                onValueChange = { handleErrors(it, null); email = it },
                label = { Text(text = "Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Email, contentDescription = "email"
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = primary,
                )
            )
            if (emailError) Spacer(modifier = Modifier.padding(3.dp))
            if (emailError) Text(
                text = "Insira um email válido*", color = errorColor, fontSize = 14.sp
            )
            Spacer(modifier = Modifier.padding(3.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(2.dp, shape = ShapeDefaults.ExtraLarge),
                enabled = !isLoading,
                shape = ShapeDefaults.ExtraLarge,
                value = password,
                onValueChange = { handleErrors(null, it); password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Senha") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Lock, contentDescription = "lock"
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = primary,
                )
            )
            if (passwordError) Spacer(modifier = Modifier.padding(3.dp))
            if (passwordError) Text(
                text = "Senha deve ter mais de 7 caracteres*", color = errorColor, fontSize = 14.sp
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(modifier = if (isLoading) Modifier
                .width(200.dp)
                .height(55.dp)
                .shadow(2.dp, shape = ShapeDefaults.ExtraLarge)
                .align(Alignment.CenterHorizontally)
            else Modifier
                .fillMaxWidth()
                .height(55.dp)
                .shadow(2.dp, shape = ShapeDefaults.ExtraLarge),
                shape = ShapeDefaults.ExtraLarge,
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Black),
                onClick = { handleLogin() }) {
                if (!isLoading) Text(text = "Entrar", fontSize = 16.sp) else {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            ClickableText(text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black, fontSize = 16.sp)) {
                    append("Não tem conta? ")
                }
                withStyle(style = SpanStyle(color = primary, fontSize = 16.sp)) {
                    append("Cadastrar")
                }
            },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { navController?.navigate("register") })
        }

    }
}