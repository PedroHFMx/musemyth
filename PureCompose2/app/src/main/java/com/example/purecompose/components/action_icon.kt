package com.example.purecompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.purecompose.services.systemLang
import com.example.purecompose.ui.theme.poppins
import com.example.purecompose.ui.theme.setColorByTheme
import com.example.purecompose.ui.theme.themeSelected

@ExperimentalMaterial3Api
@Composable
fun ActionIcon(
    icon: ImageVector,
    onClick: () -> Unit,
    isTranslate: Boolean? = false,
){
    val scHeight = LocalConfiguration.current.screenHeightDp
    val scWidth = LocalConfiguration.current.screenWidthDp

    val tm = setColorByTheme()

    Card (
        modifier = Modifier
            .background(Color.Transparent)
            .width(IntrinsicSize.Max),
        shape = RoundedCornerShape((scHeight * .015).dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation((scHeight * .006).dp),
        colors = CardDefaults.cardColors(tm.cardColor),
    ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding((scWidth * .03).dp)
            ) {
                Icon(
                    icon, null,
                    Modifier
                        .size((scHeight * .03).dp),
                    Color.White
                )
                if(isTranslate == true)
                    Spacer(modifier = Modifier.padding((scWidth * .006).dp, 0.dp))
                if (isTranslate == true)
                    Text(
                        text = if (systemLang == "pt") "PT" else "EN",
                        color = Color.White,
                        fontSize = (scHeight * .02).sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppins,
                    )
            }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun preview(){
    ActionIcon(icon = Icons.Rounded.Menu, onClick = { /*TODO*/ })
}
