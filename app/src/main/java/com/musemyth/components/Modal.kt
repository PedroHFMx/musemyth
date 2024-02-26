package com.musemyth.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.musemyth.ui.theme.errorColor
import androidx.compose.ui.unit.sp

@Composable
fun ShowModal(title: String, content: String, onDismiss: () -> Unit, onConfirm: (() -> Unit)? = null ) {
    AlertDialog(
        containerColor = Color.White,
        icon = {
        Icon(Icons.Rounded.Warning, contentDescription = "warning", tint = errorColor)
    }, title = {
        Text(text = title, fontWeight = FontWeight.Light)
    }, text = {
        Text(text = content)
    }, onDismissRequest = {
        onDismiss()
    }, confirmButton = {
        if(onConfirm != null){
        TextButton(onClick = {
            onConfirm()
        }) {
            Text("Ok")
        }
        }
    }, dismissButton = {
        TextButton(onClick = {
            onDismiss()
        }) {
            Text("Ok")
        }
    })

}

