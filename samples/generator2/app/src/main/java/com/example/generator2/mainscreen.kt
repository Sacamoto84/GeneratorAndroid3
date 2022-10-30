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
import com.example.generator2.ui.GreenLCD48
import com.example.generator2.ui.wiget.EncoderLine
import com.example.generator2.ui.wiget.UIspinner.Spinner
import kotlinx.coroutines.delay

@Composable
fun mainsreen() {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        //color = Color(0xff2E3138) //MaterialTheme.colors.background
        color = Color(0xFF897F7F) //MaterialTheme.colors.background

    ) {


        Column(modifier = Modifier.padding(top = 3.dp)) {


            //Экран
            Row(horizontalArrangement = Arrangement.Center)
            {

                calculateLCD()

                Column() {
                }


            }

            //Первый ряд кнопок
            Row(
                Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    //.background(Color.Cyan)
                , verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    Modifier
                        .size(80.dp, 80.dp)
                        .background(Color.Blue)
                )
                {}
                Spinner(
                    "CH0",
                    "CR",
                    modifier = Modifier.padding(top = 0.dp, start = 3.dp, end = 3.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {


                    val carrierFr   by Global.ch1_Carrier_Fr.observeAsState()

                    Encoder(
                        "Грубо",
                        { Global.ch1_Carrier_Fr.value = it },
                        carrierFr,
                        sensitivity = 2f,
                        maxAngle = 5000f
                    )

                    Encoder(
                        text = "Точно",
                        { Global.ch1_Carrier_Fr.value = it },
                        carrierFr,
                        sensitivity = 0.03f
                                ,
                        maxAngle = 5000f
                    )

                }
            }


            //Второй ряд кнопок AM
            Row(
                Modifier
                    .padding(top = 16.dp)
                    .height(80.dp)
                    .fillMaxWidth()
                    //.background(Color.Cyan)
                , verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    Modifier
                        .size(80.dp, 80.dp)
                        .background(Color.Blue)
                )
                {}
                Spinner(
                    "CH0",
                    "CR",
                    modifier = Modifier.padding(top = 0.dp, start = 3.dp, end = 3.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {


                    val carrierAm   by Global.ch1_AM_Fr.observeAsState()
                    Encoder(
                        "AM",
                        { Global.ch1_AM_Fr.value = it },
                        carrierAm,
                        sensitivity = 0.02f, maxAngle = 100f

                    )
                }
            }

            //Третий ряд кнопок
            Row(
                Modifier
                    .padding(top = 16.dp)
                    .height(80.dp)
                    .fillMaxWidth()
                    //.background(Color.Cyan)
                , verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    Modifier
                        .size(80.dp, 80.dp)
                        .background(Color.Blue)
                )
                {}
                Spinner(
                    "CH0",
                    "CR",
                    modifier = Modifier.padding(top = 0.dp, start = 3.dp, end = 3.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {


                    val carrierFmBase   by Global.ch1_FM_Base.observeAsState()

                    Encoder(
                        "Base Грубо",
                        { Global.ch1_FM_Base.value = it },
                        carrierFmBase, sensitivity = 2f
                    )
                    Encoder(
                        "Точно",
                        { Global.ch1_FM_Base.value = it },
                        carrierFmBase , sensitivity = 0.03f
                    )
                }
            }

            //Четверный ряд кнопок
            Row(
                Modifier
                    .padding(top = 16.dp)
                    .height(80.dp)
                    .fillMaxWidth()
                    //.background(Color.Cyan)
                , verticalAlignment = Alignment.CenterVertically
            ) {


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    val carrierFmDev   by Global.ch1_FM_Dev.observeAsState()

                    Encoder(
                        "Dev",
                        { Global.ch1_FM_Dev.value = it },
                        carrierFmDev
                    )
                    Encoder(
                        "Dev",
                        { Global.ch1_FM_Dev.value = it },
                        carrierFmDev
                    )

                    val carrierFmFr   by Global.ch1_FM_Fr.observeAsState()

                    Encoder(
                        "Fr",
                        { Global.ch1_FM_Fr.value = it },
                        carrierFmFr, sensitivity = 0.02f, maxAngle = 100f
                    )
                }
            }

        }


    }


}

@Composable
fun calculateLCD(CH: Int = 0) {


    var currentTime by remember {
        mutableStateOf(1000)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }

    val lcd = GreenLCD48()
    lcd.clear(1)
    //lcd.drawIcon(0, 0, icon1)

    //lcd.drawGliph('&'.toByte(), 41, 9, 1)
    //lcd.drawGliph('&', 1, 1)
    //lcd.string(9, 0, value = "${sliderPosition.format(1)}")
    //lcd.string(40, 0, "Hz")


    val carrierFr     by Global.ch1_Carrier_Fr.observeAsState()
    val carrierAmFr   by Global.ch1_AM_Fr.observeAsState()
    val carrierFmBase by Global.ch1_FM_Base.observeAsState()
    val carrierFmDev  by Global.ch1_FM_Dev.observeAsState()
    val carrierFmFr   by Global.ch1_FM_Fr.observeAsState()

    lcd.string(0, 0, carrierFr!!.format(1))
    lcd.string(0, 8,  "AM  ${carrierAmFr!!.format(1)}")
    lcd.string(0, 16, "Base ${carrierFmBase!!.format(1)}")
    lcd.string(0, 24, "Dev  ${carrierFmDev!!.format(1)}")
    lcd.string(0, 32, "F ${carrierFmFr!!.format(1)}")
    //lcd.drawGliph('5'.toByte(), 0, 5)


    //print("\n-------->LaunchedEffect\n")

    var update by remember {
        mutableStateOf(false)
    }

    if (update) {

        //lcd.drawGliph('6'.toByte(), 12, 1)
        //lcd.drawGliph('%'.toByte(), 41, 1)
        //lcd.setPixel(0,0,1)
    } else {
        //lcd.drawGliph('5'.toByte(), 12, 1)
        //lcd.drawGliph('&'.toByte(), 41, 1)
        //lcd.setPixel(0,0,0)
    }

    LaunchedEffect(key1 = true, key2 = isTimerRunning) {

        while (true) {
            print("-------->\n")
            delay(1000L)
            //update = !update
        }
    }

    lcd.render(
        update,
        modifier = Modifier.padding(start = 8.dp, end=8.dp)
    )


}

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