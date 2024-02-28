package com.musemyth.components

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.musemyth.ui.theme.errorColor

@Composable
@Preview
fun ShowModal(
    title: String = "Ops!",
    content: String = "Email ou senha incorretos.",
    onDismiss: () -> Unit = {},
    onConfirm: (() -> Unit)? = null,
    confirmBtnTxt: String = "Ok",
    icon: ImageVector = Icons.Rounded.Warning,
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
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    icon, contentDescription = "warning", tint = iconColor,
                    modifier = Modifier.width(36.dp)
                )
                Text(
                    text = title, fontWeight = FontWeight.Light, textAlign = TextAlign.Center,
                    fontSize = 22.sp
                )
                Text(
                    text = content, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
                Button(
                    onClick = {
                        if (onConfirm != null) {
                            onConfirm()
                        }
                    },
                    shape = ShapeDefaults.ExtraLarge,
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                ) {
                    Text(text = confirmBtnTxt, fontSize = 14.sp)
                }
            }
        }
    }

}

