package com.musemyth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.musemyth.R
import com.musemyth.services.isLoading
import com.musemyth.ui.theme.Poppins
import com.musemyth.ui.theme.primary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun Header(
    isHome: Boolean = false,
    title: String? = "Teste",
    navController: NavController? = null,
    bgColor: Color = primary,
    drawerState: DrawerState? = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val scope = rememberCoroutineScope()

    Box(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Transparent)
    ) {
        Box(
            Modifier
                .background(bgColor)
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxSize(), Arrangement.SpaceBetween, Alignment.CenterVertically
            ) {

                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxSize(), Alignment.CenterStart
                ) {
                    if (!isHome) Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(20.dp, 0.dp)
                            .clickable {
                                if (!isLoading) {
                                    navController?.popBackStack()
                                }
                            },
                        tint = Color.White
                    )

                    if (isHome) Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(20.dp, 0.dp)
                            .clickable {
                                if (drawerState != null)
                                    scope.launch {
                                        drawerState.apply {
                                             open()
                                        }
                                    }
                            },
                        tint = Color.White
                    )
                }

                if (isHome) Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    Modifier.padding(8.dp)
                )

                if (!isHome) Text(title!!, color = Color.White, fontFamily = Poppins)

                Row(
                    Modifier
                        .weight(1f)
                        .fillMaxSize(), Arrangement.End, Alignment.CenterVertically
                ) {

                }
            }
        }
    }
}