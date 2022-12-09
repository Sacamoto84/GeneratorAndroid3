package com.example.generator2.screens.firebase

import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.generator2.vm.Global

@Composable
fun ScreenFirebase(navController: NavHostController, global: Global) {

//    var username by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    TextField(value = username, onValueChange = {username = it} )
//    TextField(value = password, onValueChange = {password = it} )

    global.firebase.LoginScreen(viewModel = global)
//
//    Button(onClick = {
//
//    }) {
//    }




}

