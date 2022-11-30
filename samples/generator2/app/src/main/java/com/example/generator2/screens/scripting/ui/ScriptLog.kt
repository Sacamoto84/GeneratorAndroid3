package com.example.generator2.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.generator2.console.Console2
import com.example.generator2.vm.Global

var consoleLog = Console2()

//Нарисовать консоль Log
@Composable
fun ConsoleLogDraw(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.padding(2.dp)
            //.border(width = 1.dp, color = Color.White)
            .then(modifier)
    ) {
        Column() {
            consoleLog.Draw(
                Modifier.padding(start = 8.dp)
            )
        }
    }
}
