package com.example.generator2.screens.scripting.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.generator2.R
import com.example.generator2.theme.colorDarkBackground


@Composable
fun TemplateButtonBottomBar(
    modifier: Modifier = Modifier,
    str: String = "?",
    onClick: () -> Unit = {},
    backgroundColor: Color = colorDarkBackground,
    contentColor: Color = Color.White,
) {
    OutlinedButton(
        onClick = onClick, modifier = Modifier.fillMaxWidth().then(modifier) //.weight(1f)
            .padding(start = 8.dp, end = 4.dp), colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = backgroundColor, contentColor = contentColor
        ), border = BorderStroke(1.dp, Color.LightGray), contentPadding = PaddingValues(2.dp)
    ) {
        Text(str, maxLines = 2, fontFamily = FontFamily(Font(R.font.jetbrains)), fontSize = 14.sp)
    }
}

@Composable
fun TemplateButtonBottomBarAndLottie(
    modifier: Modifier = Modifier,
    str: String = "?",
    onClick: () -> Unit = {},
    backgroundColor: Color = colorDarkBackground,
    contentColor: Color = Color.White,
    resId: Int, //Ресурс для анимации
    autostart: Boolean = false, //При открытии или по нажатии
    iterationsInfitity: Boolean = false, //Бесконечно повторять или 1 раз
    size: Dp = 36.dp, //Размер анимации,
    paddingStart : Dp = 0.dp,
    paddingStartText : Dp = 8.dp

) {


    var nonce by remember { mutableStateOf(0) }
    //  val animatable = rememberLottieAnimatable()
    //  val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId))

    if (autostart) {
        nonce = 100
    }

    OutlinedButton(
        onClick = {
            onClick()
            nonce++
        }, modifier = Modifier.fillMaxWidth().then(modifier) //.weight(1f)
            .padding(start = 8.dp, end = 4.dp), colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = backgroundColor, contentColor = contentColor
        ), border = BorderStroke(1.dp, Color.LightGray), contentPadding = PaddingValues(2.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {


            //LottieAnimation(composition, { animatable.progress }, modifier = Modifier.padding(start = paddingStart).size(size))

            Text(
                str,
                maxLines = 2,
                fontFamily = FontFamily(Font(R.font.jetbrains)),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = paddingStartText)
            )
        }
    }
}