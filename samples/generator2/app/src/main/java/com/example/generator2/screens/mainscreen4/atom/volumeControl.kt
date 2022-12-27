package com.example.generator2.screens.mainscreen4.atom

import android.graphics.Typeface
import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat.getFont
import com.example.generator2.R
import com.example.generator2.theme.colorDarkBackground
import com.example.generator2.util.format
import com.siddroid.holi.colors.MaterialColor
import ms4SwitchWidth

lateinit var customTypeface: Typeface

@Composable
fun volumeControl() {

    val offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var height by remember { mutableStateOf(0f) }

    var boxColor by remember { mutableStateOf(MaterialColor.BLUE_400) }


    var volume by remember { mutableStateOf(0f) }

    var position by remember { mutableStateOf(0f) }

    //customTypeface = resources.getFont(R.font.c64_pro_mono_style)



    Box(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
            .height(104.dp)
            .width(ms4SwitchWidth)
            //.border(0.dp, Color.White, RoundedCornerShape(8.dp))

            //.clip(RoundedCornerShape(8.dp))

            //.background(colorDarkBackground)

            .pointerInput(Unit) {

                detectVerticalDragGestures(
                    onDragStart = {
                        //boxColor = MaterialColor.GREEN_400
                    },

                    onDragEnd = {
                        //boxColor = MaterialColor.BLUE_400
                    },

                    onVerticalDrag = { p, dragAmount ->

                        println("p.position.y " + p.position.y.toString())
                        position = p.position.y.coerceIn(0f, 104.dp.toPx() - 16.dp.toPx())
                        println("position $position")

                        volume = (1f - position / (104.dp.toPx() - 16.dp.toPx()))
                    }
                )
            }, contentAlignment = Alignment.Center
    ) {


        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            val h = size.height
            //println("h px:$h")
            //println("h dp:${h.toDp()}")

            drawRoundRect(
                color = colorDarkBackground,
                topLeft = Offset(0f, 0f),
                size = Size(size.width, size.height),
                cornerRadius = CornerRadius(8.dp.toPx(), (8.dp.toPx()))
            )

            drawRoundRect(
                color = MaterialColor.GREEN_400,
                topLeft = Offset(0f, position),
                size = Size(size.width, 16.dp.toPx()),
                cornerRadius = CornerRadius(16.dp.toPx(), (16.dp.toPx()))
            )


            val paint = android.graphics.Paint()

            paint.apply {
                textSize = 14.sp.toPx()
                color = Color.White.toArgb()//0xffff0000.toInt()
                isAntiAlias = true
                textAlign = android.graphics.Paint.Align.CENTER
                //typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    typeface = customTypeface
                }


            }

            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    (volume * 100f).format(1),
                    size.width/2,
                    position + 13.dp.toPx(),
                    paint
                )
            }


        })


    }

}

