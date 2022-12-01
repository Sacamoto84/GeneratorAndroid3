package com.example.generator2.screens.editor

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import colorDarkBackground
import colorLightBackground
import com.example.generator2.screens.editor.ui.EditorPreviewCarrier
import com.example.generator2.screens.editor.ui.EditorPreviewFM
import com.example.generator2.screens.editor.ui.*
import com.example.generator2.vm.Global
import libs.modifier.recomposeHighlighter

@Composable
fun ScreenEditor(navController: NavHostController, global: Global) {

    Column(
        Modifier.fillMaxSize() //  .recomposeHighlighter()
            .background(colorDarkBackground).verticalScroll(rememberScrollState())
    ) {

        //Preview
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth() //
                .border(
                    1.dp,
                    brush = Brush.verticalGradient(listOf(Color.Gray, Color.Gray, Color.DarkGray)),
                    RectangleShape
                ) //.clip(RoundedCornerShape(16.dp))
                .background(colorLightBackground).recomposeHighlighter()
        ) {
            EditorPreviewCarrier(model)
            EditorPreviewFM(model)
        }


        Row(
            modifier = Modifier.height(232.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.background(Color.Transparent)
            ) {

                Box(modifier = Modifier.padding(start = 16.dp, bottom = 8.dp).weight(0.5f)) {
                    ButtonPoint()
                }

                Box(modifier = Modifier.padding(start = 16.dp, top = 8.dp).weight(0.5f)) {
                    ButtonLine()
                }

            }


            val sizeCanvaChar = 8f
            val strokeWidth = 6f

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
            ) {

                Row() {

                    Column() {

                        EditorCanvasLoop()

                        //Горизонтальное усиление
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            
                            // -
                            Box(
                                Modifier.fillMaxHeight().width(70.dp).background(Color.Gray)
                                    .clickable { model.gainXXDec() },
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier, onDraw = {
                                    drawLine(
                                        start = Offset(
                                            size.width / 2 - sizeCanvaChar.dp.toPx(),
                                            size.height / 2
                                        ),
                                        end = Offset(
                                            size.width / 2 + sizeCanvaChar.dp.toPx(),
                                            size.height / 2
                                        ),
                                        strokeWidth = strokeWidth,
                                        color = Color.White,
                                        cap = StrokeCap.Round
                                    )
                                })
                            }

                            Box(
                                Modifier.fillMaxHeight().width(60.dp).background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {

                                if (model.gainXX.value < 1.0f) Text(
                                    text = model.gainXX.value.toString(),
                                    fontSize = 24.sp
                                )
                                else Text(
                                    text = model.gainXX.value.toInt().toString(),
                                    fontSize = 24.sp
                                )

                            }

                            // +
                            Box(
                                Modifier.fillMaxHeight().width(70.dp).background(Color.Gray)
                                    .clickable { model.gainXXInc() },
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier, onDraw = {
                                    drawLine(
                                        start = Offset(
                                            size.width / 2 - sizeCanvaChar.dp.toPx(),
                                            size.height / 2
                                        ),
                                        end = Offset(
                                            size.width / 2 + sizeCanvaChar.dp.toPx(),
                                            size.height / 2
                                        ),
                                        strokeWidth = strokeWidth,
                                        color = Color.White,
                                        cap = StrokeCap.Round
                                    )

                                    drawLine(
                                        start = Offset(
                                            size.width / 2,
                                            size.height / 2 - sizeCanvaChar.dp.toPx()
                                        ),
                                        end = Offset(
                                            size.width / 2,
                                            size.height / 2 + sizeCanvaChar.dp.toPx()
                                        ),
                                        strokeWidth = strokeWidth,
                                        color = Color.White,
                                        cap = StrokeCap.Round
                                    )

                                })
                            }

                        }

                    }

                    Column() {




                        Box(
                            Modifier.height(70.dp).width(30.dp).background(Color.Gray)
                                .clickable { model.gainYYInc() },
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier, onDraw = {
                                drawLine(
                                    start = Offset(
                                        size.width / 2 - sizeCanvaChar.dp.toPx(), size.height / 2
                                    ),
                                    end = Offset(
                                        size.width / 2 + sizeCanvaChar.dp.toPx(),
                                        size.height / 2
                                    ),
                                    strokeWidth = strokeWidth,
                                    color = Color.White,
                                    cap = StrokeCap.Round
                                )

                                drawLine(
                                    start = Offset(
                                        size.width / 2, size.height / 2 - sizeCanvaChar.dp.toPx()
                                    ),
                                    end = Offset(
                                        size.width / 2,
                                        size.height / 2 + sizeCanvaChar.dp.toPx()
                                    ),
                                    strokeWidth = strokeWidth,
                                    color = Color.White,
                                    cap = StrokeCap.Round
                                )

                            })
                        }
                        Box(
                            Modifier.height(60.dp).width(30.dp).background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {

                            if (model.gainYY.value < 1.0f) Text(
                                text = model.gainYY.value.toString(),
                                fontSize = 16.sp
                            )
                            else Text(
                                text = model.gainYY.value.toInt().toString(),
                                fontSize = 24.sp
                            )

                        } //+
                        Box(
                            Modifier.height(70.dp).width(30.dp).background(Color.Gray)
                                .clickable { model.gainYYDec() },
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier, onDraw = {

                                drawLine(
                                    start = Offset(
                                        size.width / 2 - sizeCanvaChar.dp.toPx(), size.height / 2
                                    ),
                                    end = Offset(
                                        size.width / 2 + sizeCanvaChar.dp.toPx(),
                                        size.height / 2
                                    ),
                                    strokeWidth = strokeWidth,
                                    color = Color.White,
                                    cap = StrokeCap.Round
                                )

                            })
                        }

                        Box(modifier = Modifier.fillMaxHeight().width(30.dp).background(Color.LightGray).clickable { model.gainYYNormalize() },
                            contentAlignment = Alignment.Center)
                        {

                            Text(text = "N", fontSize = 18.sp)
                        }

                    }


                }


            }

        }



        Box(
            modifier = Modifier.padding(8.dp).fillMaxWidth() //
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