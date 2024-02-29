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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.musemyth.R
import com.musemyth.components.Header
import com.musemyth.services.UserServices
import com.musemyth.services.fbError
import com.musemyth.services.isLoading
import com.musemyth.services.showModal
import com.musemyth.ui.theme.bgColor
import com.musemyth.ui.theme.errorColor
import com.musemyth.ui.theme.primary
import com.musemyth.utils.HandleFirebaseError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun RegisterScreen(navController: NavController? = null) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    val userServices = UserServices()

    val regexName = Regex("\\b[a-zA-Z]{3,}\\s[a-zA-Z]{3,}\\b")
    val regexEmail = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+\$")

    val nameParts = name.split(" ")

    // Use FocusRequester to request focus for the second TextField
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    // Use SoftwareKeyboardController to hide the keyboard
    val keyboardController = LocalSoftwareKeyboardController.current

    fun handleErrors(
        emailI: String? = null, passwordI: String? = null, conPasswordI: String? = null,
        nameI: String? = null
    ) {
        if (emailI != null) {
            emailError = !regexEmail.matches(emailI.trim())
        }
        if (passwordI != null) {
            passwordError = passwordI.length <= 5
        }
        if (conPasswordI != null) {
            confirmPasswordError = conPasswordI != passwordI || conPasswordI == ""
        }
        if (nameI != null) {
            nameError = !regexName.matches(nameI.trim()) || !nameI.trim().contains(" ")
        }
    }

    fun handleRegisterUser() {
        if (name.matches(regexName) && email.matches(regexEmail) && password.length >= 6
            && confirmPassword == password
        ) {
            userServices.register(email.trim(), password, "${nameParts[0].trim()[0].uppercase()}${
                nameParts[0].trim().substring(1).lowercase()
            } ${nameParts[1].trim()[0].uppercase()}${
                nameParts[1].trim().substring(1).lowercase()
            }", navController!!)
        }
    }

    if(showModal){
        HandleFirebaseError(fbError)
    }

    Column(
        Modifier
            .fillMaxSize()
            .blur(if (showModal) 20.dp else 0.dp)
            .background(bgColor),
    ) {
        Header(title = "Cadastro", navController = navController, bgColor = primary)
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState(0))
                .padding(16.dp),
            Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), contentDescription = "Logo",
                colorFilter = ColorFilter.tint(primary),
                modifier = Modifier
                    .size(200.dp, 100.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Spacer(modifier = Modifier.padding(5.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, shape = ShapeDefaults.ExtraLarge),
                shape = ShapeDefaults.Large,
                value = name,
                onValueChange = { handleErrors(nameI = it.trim()); if (it.length <= 28) name = it },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { emailFocusRequester.requestFocus() }
                ),
                label = { Text(text = "Nome") },
                leadingIcon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = "") },
                trailingIcon = {
                    if (name.isNotEmpty()) {
                        IconButton(onClick = { name = "" }) {
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
                )
            )
            Spacer(modifier = Modifier.padding(5.dp))
            if (nameError) Text(
                text = "Insira primeiro nome e último nome*", color = errorColor, fontSize = 14.sp
            )
            if (nameError) Spacer(modifier = Modifier.padding(10.dp))
            TextField(
                modifier = Modifier
                    .focusRequester(emailFocusRequester)
                    .fillMaxWidth()
                    .shadow(2.dp, shape = ShapeDefaults.ExtraLarge),
                shape = ShapeDefaults.Large,
                value = email,
                onValueChange = { handleErrors(emailI = it.trim()); email = it },
                label = { Text(text = "Email") },
                leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = "") },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { passwordFocusRequester.requestFocus() }
                ),
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
                )
            )
            Spacer(modifier = Modifier.padding(5.dp))
            if (emailError) Text(
                text = "Insira um email válido*", color = errorColor, fontSize = 14.sp
            )
            if (emailError) Spacer(modifier = Modifier.padding(10.dp))
            TextField(
                modifier = Modifier
                    .focusRequester(passwordFocusRequester)
                    .fillMaxWidth()
                    .shadow(2.dp, shape = ShapeDefaults.ExtraLarge),
                shape = ShapeDefaults.Large,
                value = password,
                onValueChange = { handleErrors(passwordI = it); password = it },
                label = { Text(text = "Senha") },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { confirmPasswordFocusRequester.requestFocus() }
                ),
                leadingIcon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = "") },
                visualTransformation =
                if (showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector =
                            if (showPassword) Icons.Rounded.VisibilityOff
                            else Icons.Rounded.Visibility,
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
                )
            )
            Spacer(modifier = Modifier.padding(5.dp))
            if (passwordError) Text(
                text = "Mínimo de 6 caracteres*", color = errorColor, fontSize = 14.sp
            )
            if (passwordError) Spacer(modifier = Modifier.padding(10.dp))
            TextField(
                modifier = Modifier
                    .focusRequester(confirmPasswordFocusRequester)
                    .fillMaxWidth()
                    .shadow(2.dp, shape = ShapeDefaults.ExtraLarge),
                shape = ShapeDefaults.Large,
                value = confirmPassword,
                onValueChange = {
                    handleErrors(
                        conPasswordI = it,
                        passwordI = password
                    ); confirmPassword = it
                },
                label = { Text(text = "Confirmar Senha") },
                leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "") },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        handleErrors(email, password, confirmPassword, name)
                        handleRegisterUser() }
                ),
                visualTransformation =
                if (showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector =
                            if (showPassword) Icons.Rounded.VisibilityOff
                            else Icons.Rounded.Visibility,
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
                )
            )
            if (confirmPasswordError) Spacer(modifier = Modifier.padding(5.dp))
            if (confirmPasswordError) Text(
                text = "As senhas devem ser iguais*", color = errorColor, fontSize = 14.sp
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                modifier = if (isLoading) Modifier
                    .width(200.dp)
                    .height(55.dp)
                    .shadow(2.dp, shape = ShapeDefaults.ExtraLarge)
                    .align(Alignment.CenterHorizontally)
                else Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .shadow(2.dp, shape = ShapeDefaults.ExtraLarge),
                colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Black),
                enabled = !isLoading,
                onClick = {
                    handleErrors(
                        email,
                        password,
                        confirmPassword,
                        name,
                    ); handleRegisterUser()
                }) {
                if (!isLoading) Text(text = "Cadastrar", fontSize = 16.sp) else {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }
}