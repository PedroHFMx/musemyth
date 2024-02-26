package com.example.purecompose.services

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.purecompose.components.EText

var showBottomSheet by mutableStateOf(false)

@Composable
fun ChangeLangWiget(){
    val scHeight = LocalConfiguration.current.screenHeightDp
    val scope = rememberCoroutineScope()
    ConstraintLayout(
        modifier = Modifier.padding(top = (scHeight * .1).dp, start = (scHeight * .1).dp)
    ) {
        val (
            cButton,
        ) = createRefs()
        DropdownMenu(
            expanded = showBottomSheet,
            onDismissRequest = { showBottomSheet = false },
            modifier = Modifier
                .constrainAs(cButton) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            DropdownMenuItem(text = { EText("English", color = Color.Black) },
                onClick = {
                    showBottomSheet = false
                    systemLang = "en"
                })
            DropdownMenuItem(text = { EText("Português", color = Color.Black) },
                onClick = {
                    showBottomSheet = false
                    systemLang = "pt"
                })

        }
    }
}