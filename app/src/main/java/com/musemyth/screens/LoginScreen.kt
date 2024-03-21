package com.musemyth.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.musemyth.R
import com.musemyth.services.UserServices
import com.musemyth.services.fbError
import com.musemyth.services.isLoading
import com.musemyth.services.showModal
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.bgColor
import com.musemyth.ui.theme.errorColor
import com.musemyth.ui.theme.primary
import com.musemyth.utils.HandleFirebaseError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun LoginScreen(navController: NavController? = null) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val regexEmail = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+\$")

    val userServices = UserServices()

    fun handleLogin() {
        if (regexEmail.matches(email.trim()) && password.length >= 6) {
            userServices.login(email.trim(), password, navController!!)
        }
    }

    fun handleErrors(emailI: String? = null, passwordI: String? = null) {
        if (emailI != null) {
            emailError = !regexEmail.matches(emailI.trim())
        }
        if (passwordI != null) {
            passwordError = passwordI.length < 6
        }
    }

    if (showModal) {
        HandleFirebaseError(fbError)
    }

    // Use FocusRequester to request focus for the second TextField
    val focusRequester = remember { FocusRequester() }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(0))
            .blur(if (showModal) 20.dp else 0.dp)
            .background(bgColor)
            .padding(16.dp), Arrangement.Center
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
                .shadow(2.dp, shape = RoundedCornerShape(10.dp)),
            enabled = !isLoading,
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontFamily = Poppins
            ),
            shape = RoundedCornerShape(10.dp),
            value = email,
            onValueChange = { handleErrors(it.trim(), null); email = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
                }
            ),
            label = { Text(text = "Email", fontSize = 15.sp) },
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
            text = "Insira um email válido*", color = errorColor, fontSize = 15.sp
        )
        Spacer(modifier = Modifier.padding(3.dp))
        TextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .height(60.dp)
                .shadow(2.dp, shape = RoundedCornerShape(10.dp)),
            enabled = !isLoading,
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontFamily = Poppins
            ),
            value = password,
            onValueChange = { handleErrors(null, it); password = it },
            visualTransformation =
            if (showPassword) VisualTransformation.None
            else PasswordVisualTransformation(),
            label = { Text(text = "Senha", fontSize = 15.sp) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(
                onDone = {
                    handleErrors(email, password)
                    handleLogin()
                }
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Lock, contentDescription = "lock"
                )
            },
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector =
                        if (showPassword) Icons.Rounded.Visibility
                        else Icons.Rounded.VisibilityOff,
                        contentDescription =
                        "Password Visibility"
                    )
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
        if (passwordError) Spacer(modifier = Modifier.padding(3.dp))
        if (passwordError) Text(
            text = "Mínimo de 6 caracteres*", color = errorColor, fontSize = 15.sp
        )
        Spacer(modifier = Modifier.padding(4.dp))
        TextButton(modifier = Modifier.align(Alignment.End), enabled = !isLoading, onClick = {
            navController?.navigate("password")
        }) {
            Text(
                text = "Esqueci minha senha",
                color = Color.Black,
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.padding(4.dp))
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
            onClick = { handleErrors(email, password); handleLogin() }) {
            if (!isLoading) Text(text = "Entrar", fontSize = 15.sp) else {
                CircularProgressIndicator(color = Color.White)
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        TextButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { navController?.navigate("register") },
            enabled = !isLoading
        ) {
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black, fontSize = 15.sp)) {
                    append("Não tem conta? ")
                }
                withStyle(style = SpanStyle(color = primary, fontSize = 15.sp)) {
                    append("Cadastrar")
                }
            })

        }

    }
}