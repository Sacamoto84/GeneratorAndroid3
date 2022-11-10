package com.example.generator2.editor

import EditorCanvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ScreenEditor()
{
    
    Column(
        Modifier.fillMaxSize()
        //  .recomposeHighlighter()
        .background(Color.Cyan)
    ) {

     
        Text(text = "Editor")


        EditorCanvas()

    }
    
}