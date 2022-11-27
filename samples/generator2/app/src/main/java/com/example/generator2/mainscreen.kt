package com.example.generator2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.generator2.ui.wiget.EncoderLine
import com.example.generator2.ui.wiget.UIspinner.Spinner


//@Composable
//fun calculateLCD(CH: Int = 0) {
//
//    var currentTime by remember {
//        mutableStateOf(1000)
//    }
//    var isTimerRunning by remember {
//        mutableStateOf(false)
//    }
//
//    val lcd = GreenLCD48()
//    lcd.clear(1)
//    //lcd.drawIcon(0, 0, icon1)
//
//    //lcd.drawGliph('&'.toByte(), 41, 9, 1)
//    //lcd.drawGliph('&', 1, 1)
//    //lcd.string(9, 0, value = "${sliderPosition.format(1)}")
//    //lcd.string(40, 0, "Hz")
//
//
//    val carrierFr     by Global.ch1_Carrier_Fr.observeAsState()
//    val carrierAmFr   by Global.ch1_AM_Fr.observeAsState()
//    val carrierFmBase by Global.ch1_FM_Base.observeAsState()
//    val carrierFmDev  by Global.ch1_FM_Dev.observeAsState()
//    val carrierFmFr   by Global.ch1_FM_Fr.observeAsState()
//
//    lcd.string(0, 0, carrierFr!!.format(1))
//    lcd.string(0, 8,  "AM  ${carrierAmFr!!.format(1)}")
//    lcd.string(0, 16, "Base ${carrierFmBase!!.format(1)}")
//    lcd.string(0, 24, "Dev  ${carrierFmDev!!.format(1)}")
//    lcd.string(0, 32, "F ${carrierFmFr!!.format(1)}")
//    //lcd.drawGliph('5'.toByte(), 0, 5)
//
//
//    //print("\n-------->LaunchedEffect\n")
//
//    var update by remember {
//        mutableStateOf(false)
//    }
//
//    if (update) {
//
//        //lcd.drawGliph('6'.toByte(), 12, 1)
//        //lcd.drawGliph('%'.toByte(), 41, 1)
//        //lcd.setPixel(0,0,1)
//    } else {
//        //lcd.drawGliph('5'.toByte(), 12, 1)
//        //lcd.drawGliph('&'.toByte(), 41, 1)
//        //lcd.setPixel(0,0,0)
//    }
//
//    LaunchedEffect(key1 = true, key2 = isTimerRunning) {
//
//        while (true) {
//            print("-------->\n")
//            delay(1000L)
//            //update = !update
//        }
//    }
//
//    lcd.render(
//        update,
//        modifier = Modifier.padding(start = 8.dp, end=8.dp)
//    )
//}

@Composable
fun Encoder(text: String = "", onValueChange: (Float) -> Unit = {}, value: Float?, sensitivity: Float = 0.4f, maxAngle: Float = 0f) {
    Column(
        //modifier = Modifier.background(Color.Red)
    ) {
        Text(text = text)

        EncoderLine(
            modifier = Modifier
                .padding(start = 0.dp)
                .size(80.dp, 80.dp),
            //.background(color = Color.Red)
            //.scale(1f)

            imageThump = ImageBitmap.imageResource(id = R.drawable.knob8thumb),
            imageThumpSize = 10.dp,
            imageThumbOffset = Offset(0f, -20f),

            imageBG = ImageBitmap.imageResource(id = R.drawable.knob8bg),   //Неподвижная часть
            imageBGSize = 100.dp,
            imageBGOffset = Offset(10f, 7f),

            //drawMeasureLine = true,
            //drawMeasureCircle = true,
            //drawMeasureDot = true
            rangeAngle = maxAngle,
            sensitivity = sensitivity,
            onValueChange = onValueChange,
            value = value
        )
    }
}