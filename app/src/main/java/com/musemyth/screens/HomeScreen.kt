package com.musemyth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Article
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.musemyth.R
import com.musemyth.components.Header
import com.musemyth.components.ShowModal
import com.musemyth.services.UserServices
import com.musemyth.services.isLoadingUser
import com.musemyth.services.showModal
import com.musemyth.services.user
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.errorColor
import com.musemyth.ui.theme.primary
import com.musemyth.ui.theme.secondary
import com.musemyth.ui.theme.statusBarColor
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HomeScreen(navController: NavController? = null) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(statusBarColor)

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val userServices = UserServices()

    Scaffold { innerPadding ->
        ModalNavigationDrawer(
            modifier = Modifier.padding(innerPadding), drawerContent = {
                Box(
                    Modifier, Alignment.CenterStart
                ) {
                    ModalDrawerSheet(
                        Modifier.fillMaxWidth(0.75f)
                    ) {
                        if (showModal) {
                            ShowModal(
                                onDismiss = { showModal = false },
                                icon = Icons.AutoMirrored.Rounded.ExitToApp,
                                title = "Sair da conta?",
                                content = "Tem certeza que deseja fazer logout?",
                                onConfirm = {
                                    showModal = false; userServices.signOut(navController!!)
                                },
                                twoButtons = true,
                                dismissTxtBtn = "Não",
                                confirmBtnTxt = "Sim",
                            )
                        }
                        Column(Modifier.fillMaxSize().background(Color.White)) {
                            Surface( contentColor = Color.White, color = Color.Transparent) {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .background(primary),
                                    Alignment.CenterStart
                                ) {
                                    Row(
                                        Modifier.padding(16.dp, 24.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    ) {
                                        Box(
                                            Modifier
                                                .background(
                                                    Color.White, shape = ShapeDefaults.ExtraLarge
                                                )
                                                .size(35.dp), Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Person,
                                                tint = primary,
                                                contentDescription = "account"
                                            )
                                        }
                                        if (isLoadingUser) LinearProgressIndicator()
                                        if (!isLoadingUser) Column {
                                            Text(
                                                text = "${user.name}",
                                                fontFamily = Poppins,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                            Text(
                                                text = "${user.email}",
                                                fontFamily = Poppins,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                            Text(
                                                text = "${user.accountType}",
                                                fontFamily = Poppins,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                    }

                                }
                            }
                            Surface {
                                Column (Modifier.background(Color.White)){
                                    if (user.accountType == "aluno") Card(
                                        Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                scope.launch {
                                                    drawerState.apply {
                                                        close().apply {
                                                            navController?.navigate(
                                                                "userStory"
                                                            )
                                                        }
                                                    }
                                                }

                                            }, colors = CardDefaults.cardColors(
                                            containerColor = Color.Transparent
                                        )
                                    ) {
                                        Row(
                                            Modifier.padding(16.dp),
                                            Arrangement.spacedBy(10.dp),
                                            Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Rounded.Article,
                                                contentDescription = "account",
                                                Modifier.size(20.dp),
                                                tint = secondary,
                                            )
                                            Text(
                                                text = "Storylines Salvos",
                                                fontFamily = Poppins,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                    }
                                    if (user.accountType == "aluno") Card(
                                        Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                scope.launch {
                                                    drawerState.apply {
                                                        close().apply {
                                                            navController?.navigate(
                                                                "userChar"
                                                            )
                                                        }
                                                    }
                                                }
                                            }, colors = CardDefaults.cardColors(
                                            containerColor = Color.Transparent
                                        )
                                    ) {
                                        Row(
                                            Modifier.padding(16.dp),
                                            Arrangement.spacedBy(10.dp),
                                            Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Person,
                                                contentDescription = "account",
                                                Modifier.size(20.dp),
                                                tint = primary,
                                            )
                                            Text(
                                                text = "Personagens Salvos",
                                                fontFamily = Poppins,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                    }
                                    if (user.accountType == "professor") Spacer(
                                        modifier = Modifier.padding(
                                            top = 16.dp
                                        )
                                    )
                                    if (user.accountType == "aluno") Divider(Modifier.padding(16.dp))
                                    Card(
                                        Modifier
                                            .fillMaxWidth()
                                            .clickable { /* TODO */ },
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.Transparent
                                        )
                                    ) {
                                        Row(
                                            Modifier.padding(16.dp),
                                            Arrangement.spacedBy(10.dp),
                                            Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Info,
                                                contentDescription = "account",
                                                Modifier.size(20.dp),
                                                tint = Color.Black,
                                            )
                                            Text(
                                                text = "Explicação Sobre Storylines",
                                                fontSize = 14.sp,
                                                fontFamily = Poppins,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                    }
                                    Divider(Modifier.padding(16.dp))
                                    Card(
                                        Modifier
                                            .fillMaxWidth()
                                            .clickable { showModal = true },
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.Transparent
                                        )
                                    ) {
                                        Row(
                                            Modifier.padding(16.dp),
                                            Arrangement.spacedBy(10.dp),
                                            Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                                                contentDescription = "account",
                                                Modifier.size(20.dp),
                                                tint = errorColor,
                                            )
                                            Text(
                                                text = "Sair da Conta",
                                                fontFamily = Poppins,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }, drawerState = drawerState, gesturesEnabled = true
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                Header(isHome = true, drawerState = drawerState)

                if (user.accountType == "aluno") {
                    Column(Modifier.padding(0.dp), Arrangement.spacedBy(0.dp)) {

                        Button(
                            onClick = { navController!!.navigate("preGenStory") },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = secondary)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Image(
                                    modifier = Modifier.size(170.dp),
                                    painter = painterResource(id = R.drawable.storyline_generate),
                                    contentDescription = "",
                                )
                                Text(
                                    text = "Gerar Storyline",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Button(
                            onClick = { navController!!.navigate("preGenChar") },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Image(
                                    modifier = Modifier.size(170.dp),
                                    painter = painterResource(id = R.drawable.character_generate),
                                    contentDescription = "",
                                )
                                Text(
                                    text = "Gerar Personagem",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                } else {
                    ProfessorHomeScreen(navController = navController!!)
                }
            }
        }
    }
}
