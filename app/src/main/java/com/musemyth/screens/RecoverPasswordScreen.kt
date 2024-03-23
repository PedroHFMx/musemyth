package com.musemyth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.musemyth.components.Header
import com.musemyth.components.ShowModal
import com.musemyth.services.UserServices
import com.musemyth.services.isLoading
import com.musemyth.services.showModal
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.bgColor
import com.musemyth.ui.theme.errorColor
import com.musemyth.ui.theme.primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun RecoverPasswordScreen(navController: NavController? = null) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }

    val regexEmail = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+\$")

    val userServices = UserServices()

    fun handleErrors(email: String) {
        emailError = !regexEmail.matches(email)
    }

    if (showModal) {
        ShowModal(
            onDismiss = { showModal = false; isLoading = false },
            onConfirm = {
                showModal = false; navController!!.popBackStack(); isLoading = false
            },
            icon = Icons.Rounded.CheckCircle,
            iconColor = Color.Green,
            title = "Sucesso!",
            content = "Se o email inserido corresponde a um email cadastrado, uma recuperação de senha foi enviada a ele, verifique a caixa de span e a lixeira."
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .blur(if (showModal) 20.dp else 0.dp)
            .background(bgColor),
    ) {
        Header(title = "Recuperar Senha", navController = navController, bgColor = primary)
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState(0))
                .padding(16.dp),
            Arrangement.Center,
        ) {
            Text(
                text = "Insira seu email de cadastro:",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .shadow(2.dp, shape = RoundedCornerShape(10.dp)),
                enabled = !isLoading,
                shape = RoundedCornerShape(10.dp),
                value = email,
                onValueChange = { handleErrors(it.trim()); email = it },
                textStyle = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = Poppins
                ),
                placeholder = { Text(text = "Email", fontSize = 15.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Email, contentDescription = "email"
                    )
                },
                trailingIcon = {
                    if (email.isNotEmpty()) {
                        IconButton(onClick = { email = "" }) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "clear text"
                            )
                        }

                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = primary,
                    focusedTrailingIconColor = primary,
                )
            )
            if (emailError) Spacer(modifier = Modifier.padding(3.dp))
            if (emailError) Text(
                text = "Insira um email válido*", color = errorColor, fontSize = 14.sp
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(modifier = if (isLoading) Modifier
                .width(200.dp)
                .height(55.dp)
                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
                .align(Alignment.CenterHorizontally)
            else Modifier
                .fillMaxWidth()
                .height(55.dp)
                .shadow(2.dp, shape = RoundedCornerShape(10.dp)),
                shape = RoundedCornerShape(10.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Black),
                onClick = {
                    handleErrors(email); if (email.matches(regexEmail)) {
                    userServices.recoverPassword(email.trim(), navController!!)
                }
                }) {
                if (!isLoading) Text(text = "Enviar Recuperação", fontSize = 15.sp) else {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }
}