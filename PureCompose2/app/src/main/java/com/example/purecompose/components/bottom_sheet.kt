package com.example.purecompose.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheet(){
    Box (
        modifier = Modifier.size(200.dp)
    ){
        Scaffold {
            Column {
                Button(onClick = { /*TODO*/ }) {

                }
                Button(onClick = { /*TODO*/ }) {

                }
            }
        }
    }
}