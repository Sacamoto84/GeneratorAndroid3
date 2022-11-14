package com.example.generator2.editor

import ButtonLine
import ButtonPoint
import EditorCanvas
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import colorDarkBackground
import colorLightBackground
import com.example.generator2.ui.theme.Green400
import com.example.generator2.ui.theme.Orange400
import com.example.generator2.ui.theme.Red400
import model
import java.util.concurrent.CancellationException

@Composable
fun ScreenEditor() {

    Column(
        Modifier.fillMaxSize() //  .recomposeHighlighter()
            .background(colorDarkBackground)
    ) {


        ButtonPoint()
        ButtonLine()

        EditorCanvas()





        //editorPreviewCarrier(model)

    }

}