package com.example.generator2.editor

import ButtonLine
import ButtonPoint
import EditorCanvas
import EditorCanvasLoop
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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


        //Рендер картинки модуляции
        Box(
            modifier = Modifier.padding(16.dp).fillMaxWidth() //
                .border(
                    1.dp,
                    brush = Brush.verticalGradient(listOf(Color.Gray, Color.Gray, Color.DarkGray)),
                    RectangleShape
                ) //.clip(RoundedCornerShape(16.dp))
                .background(colorLightBackground)
        ) {

            EditorPreviewCarrier(model)
        }

        //Рендер картинки частотной модуляции
        Box(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp).fillMaxWidth() //
                .border(
                    1.dp,
                    brush = Brush.verticalGradient(listOf(Color.Gray, Color.Gray, Color.DarkGray)),
                    RectangleShape
                ) //.clip(RoundedCornerShape(16.dp))
                .background(colorLightBackground)
        ) {
            EditorPreviewFM(model)
        }

        Row(
            modifier = Modifier.height(232.dp).fillMaxWidth().background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.background(Color.Transparent)
            ) {
                Box(modifier = Modifier.padding(start = 16.dp, bottom = 8.dp).weight(1f)) {
                    ButtonPoint()
                }
                Box(modifier = Modifier.padding(start = 16.dp, top = 8.dp).weight(1f)) {
                    ButtonLine()
                }
            }
            EditorCanvasLoop()
        }

        Box(
            modifier = Modifier.padding(16.dp).fillMaxWidth() //
                .border(
                    1.dp,
                    brush = Brush.verticalGradient(listOf(Color.Gray, Color.Gray, Color.DarkGray)),
                    RectangleShape
                ) //.clip(RoundedCornerShape(16.dp))
                .background(colorLightBackground)
        ) {
            EditorCanvas()
        }

    }

}