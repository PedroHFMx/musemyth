package com.musemyth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.musemyth.ui.theme.errorColor
import com.musemyth.ui.theme.primary

@Composable
@Preview
fun ShowModal(
    title: String = "Ops!",
    content: String = "Email ou senha incorretos.",
    onDismiss: () -> Unit = {},
    twoButtons: Boolean = false,
    onConfirm: (() -> Unit)? = null,
    confirmBtnTxt: String = "Ok",
    dismissTxtBtn: String = "Ok",
    icon: ImageVector = Icons.Rounded.Error,
    iconColor: Color = errorColor
) {
    Dialog(
        onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.elevatedCardElevation(2.dp),
            shape = ShapeDefaults.Large,
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    icon, contentDescription = "warning", tint = iconColor,
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = title, fontWeight = FontWeight.Light, textAlign = TextAlign.Center,
                    fontSize = 22.sp
                )
                Text(
                    text = content, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
                Row {
                    if (twoButtons)
                        Button(
                            onClick = {
                                onDismiss()
                            },
                            Modifier
                                .height(50.dp)
                                .shadow(2.dp, shape = ShapeDefaults.ExtraLarge)
                                .weight(1f),
                            shape = ShapeDefaults.ExtraLarge,
                            elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White,
                                contentColor = Color.Black),
                        ) {
                            Text(text = dismissTxtBtn, fontSize = 14.sp)
                        }
                    if(twoButtons) Spacer(modifier = Modifier.padding(4.dp))
                    Button(
                        onClick = {
                            if (onConfirm != null) {
                                onConfirm()
                            }
                        },
                        if (!twoButtons) Modifier
                            .height(50.dp)
                            .shadow(2.dp, shape = ShapeDefaults.ExtraLarge)
                            .widthIn(min = 100.dp)
                        else Modifier
                            .height(50.dp)
                            .shadow(2.dp, shape = ShapeDefaults.ExtraLarge)
                            .weight(1f),
                        shape = ShapeDefaults.ExtraLarge,
                        elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if(twoButtons) errorColor else primary)
                    ) {
                        Text(text = confirmBtnTxt, fontSize = 14.sp)
                    }
                }
            }
        }
    }

}

