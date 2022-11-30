package com.example.generator2.screens.editor

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import colorDarkBackground
import colorLightBackground
import com.airbnb.lottie.compose.*
import com.example.generator2.R
import com.example.generator2.screens.editor.ui.ButtonLine
import com.example.generator2.screens.editor.ui.ButtonPoint
import com.example.generator2.screens.editor.ui.EditorCanvas
import com.example.generator2.vm.Global
import libs.modifier.recomposeHighlighter

@Composable
fun ScreenEditor(navController: NavHostController, global: Global) {

    Column(
        Modifier.fillMaxSize() //  .recomposeHighlighter()
            .background(colorDarkBackground).verticalScroll(rememberScrollState())
    ) {


        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.alert))
        LottieAnimation(
            composition,
            modifier = Modifier.size(100.dp),
            iterations = LottieConstants.IterateForever
        )



        val composition1 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.delete))
        var nonce by remember { mutableStateOf(1) }
        val animatable = rememberLottieAnimatable()

        LaunchedEffect(composition, nonce) {
            composition1 ?: return@LaunchedEffect
            animatable.animate(
                composition,
                continueFromPreviousAnimate = false,
            )
        }
        LottieAnimation(
            composition1,
            { animatable.progress },
            modifier = Modifier
                .clickable { nonce++ }
        )


//        LottieAnimation(
//            composition  = composition1,
//            modifier = Modifier.size(100.dp),
//            isPlaying = isPlaying.value,
//            restartOnPlay = true,
//            iterations = 1
//        )

        Button(onClick = {
            nonce++
        }) { Text(text = nonce.toString())}

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
            } //EditorCanvasLoop()
        }

        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth() //
                .border(
                    1.dp,
                    brush = Brush.verticalGradient(listOf(Color.Gray, Color.Gray, Color.DarkGray)),
                    RectangleShape
                ) //.clip(RoundedCornerShape(16.dp))
                .background(colorLightBackground).recomposeHighlighter()
        ) { //EditorPreviewCarrier(model)
            //EditorPreviewFM(model)
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