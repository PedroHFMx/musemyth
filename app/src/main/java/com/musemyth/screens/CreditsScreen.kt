package com.musemyth.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.musemyth.R
import com.musemyth.components.Header
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.primary
import com.musemyth.ui.theme.tertiary

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CreditsScreen(navController: NavController? = null) {
    val getVersion = LocalContext.current.packageManager.getPackageInfo(
        LocalContext.current.packageName, 0
    ).versionName
    val uriHandler = LocalUriHandler.current
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Header(title = "Créditos", navController = navController)
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            Arrangement.SpaceAround,
            Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .background(tertiary, CircleShape)
                    .size(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "logo",
                    colorFilter = ColorFilter.tint(primary)
                )
            }
            Text(
                text = "Musemyth, aplicativo para geração de Storylines",
                textAlign = TextAlign.Center,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Criado por:\nAntonio Henrique Garcia Vieira\nantoniohgvieira@gmail.com",
                textAlign = TextAlign.Center,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal
            )
            Text(
                modifier = Modifier.clickable { uriHandler.openUri("https://www.instagram.com/pedro_hfurlanm/") },
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(
                            "Desenvolvido por:\nPedro Henrique Furlan de Matos\n"
                        )
                    }
                    withStyle(style = SpanStyle(color = primary, textDecoration = TextDecoration.Underline)) {
                        append("@pedro_hfurlanm")
                    }
                },
                textAlign = TextAlign.Center,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "Versão: $getVersion",
                textAlign = TextAlign.Center,
                fontFamily = Poppins,
                fontWeight = FontWeight.Light
            )
        }
    }
}