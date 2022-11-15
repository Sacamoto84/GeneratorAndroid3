package com.example.generator2.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun EditorPreviewCarrier(model: EditorMatModel) {
//    Box(
//        modifier = Modifier.padding(8.dp).fillMaxWidth().aspectRatio(4f).background(Color.Black)
//    ) {
//
//        Canvas(modifier = Modifier.fillMaxSize()) {
//
//            val points = mutableListOf<Offset>()
//
//            val sizeW = size.width.toInt()
//
//            for (x in 0 until size.width.toInt() / 2) {
//                val mapX: Int = model.map(x, 0, sizeW / 2 - 1, 0, model.editWight - 1)
//                val y =
//                    model.map(model.signal[mapX], 0, model.editMax, 0, size.height.toInt() - 1).toFloat() / 2
//                points.add(Offset(x.toFloat(), y))
//                points.add(Offset(x.toFloat(), size.height.toInt() - y))
//            }
//
//            for (x in size.width.toInt() / 2 until size.width.toInt()) {
//                val mapX: Int = model.map(x, sizeW / 2, sizeW - 1, 0, model.editWight - 1)
//                val y =
//                    model.map(model.signal[mapX], 0, model.editMax, 0, size.height.toInt() - 1).toFloat() / 2
//                points.add(Offset(x.toFloat(), y))
//                points.add(Offset(x.toFloat(), size.height.toInt() - y))
//            }
//
//            drawPoints(
//                brush = Brush.linearGradient(
//                    colors = listOf(Color.Magenta, Color.Yellow)
//                ),
//                points = points,
//                cap = StrokeCap.Round,
//                pointMode = PointMode.Lines,
//                strokeWidth = 4f
//            )
//
//            //Вертикальная линия
//            drawLine(
//                color = Color.DarkGray,
//                start = Offset(size.width / 2, 0f),
//                end = (Offset(size.width / 2, size.height - 1))
//            )
//
//            //Горизонтальная линия
//            drawLine(
//                color = Color.DarkGray,
//                start = Offset(0f, size.height / 2),
//                end = (Offset(size.width - 1, size.height / 2))
//            )
//        }
//    }
}

@Composable
fun EditorPreviewFM(model: EditorMatModel) {

//    Box(
//        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp).fillMaxWidth().aspectRatio(4f).background(Color.Black)
//    ) {
//
//        Canvas(modifier = Modifier.fillMaxSize()) {
//
//            val points = mutableListOf<Offset>()
//
//            val sizeW = size.width.toInt()
//
//            for (x in 0 until size.width.toInt() / 2) {
//                val mapX: Int = model.map(x, 0, sizeW / 2 - 1, 0, model.editWight - 1)
//                val y =
//                    model.map(model.signal[mapX], 0, model.editMax, 0, size.height.toInt() - 1).toFloat()
//                points.add(Offset(x.toFloat(), y))
//
//            }
//
//            for (x in size.width.toInt() / 2 until size.width.toInt()) {
//                val mapX: Int = model.map(x, sizeW / 2, sizeW - 1, 0, model.editWight - 1)
//                val y =
//                    model.map(model.signal[mapX], 0, model.editMax, 0, size.height.toInt() - 1).toFloat()
//                points.add(Offset(x.toFloat(), y))
//
//            }
//
//            drawPoints(
//                brush = Brush.linearGradient(
//                    colors = listOf(Color.Magenta, Color.Yellow)
//                ),
//                points = points,
//                cap = StrokeCap.Round,
//                pointMode = PointMode.Polygon,
//                strokeWidth = 4f
//            )
//
//            //Вертикальная линия
//            drawLine(
//                color = Color.DarkGray,
//                start = Offset(size.width / 2, 0f),
//                end = (Offset(size.width / 2, size.height - 1))
//            )
//
//            //Горизонтальная линия
//            drawLine(
//                color = Color.DarkGray,
//                start = Offset(0f, size.height / 2),
//                end = (Offset(size.width - 1, size.height / 2))
//            )
//        }
//    }
}