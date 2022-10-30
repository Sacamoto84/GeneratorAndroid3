package com.example.generator2.mainscreen4

import BottomBarSave
import CardCarrier
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import colorDarkBackground
import com.example.generator2.Global.bottomBarRoute
import libs.modifier.recomposeHighlighter


//var bottomBarRoute : bottomBarEnum = bottomBarEnum.HOME

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun mainsreen4() {
    Scaffold(
        bottomBar = {
            when (bottomBarRoute.value) {
                bottomBarEnum.HOME -> BottomBarGenerator()
                bottomBarEnum.SAVE   -> BottomBarSave()
                bottomBarEnum.SAVEAS -> BottomBarSave()
                bottomBarEnum.LOAD   -> BottomBarLoad()
                bottomBarEnum.LOADAS -> BottomBarLoad()
                else -> BottomBarGenerator()
            }
        },
        backgroundColor = colorDarkBackground
    )
    {


        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding())
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            when (bottomBarRoute.value) {

                bottomBarEnum.HOME -> {
                    CardCarrier("CH0")
                    CardCarrier("CH1")
                }

                bottomBarEnum.SAVEAS -> {

                    Box(modifier = Modifier.fillMaxSize().background(Color.Red))
                    {
                        Text("SAVEAS")
                    }

                }

                bottomBarEnum.LOADAS -> {

                    Box(modifier = Modifier.fillMaxSize().background(Color.Green))
                    {
                        Text("LOADAS")
                    }

                }

                else -> {
                    CardCarrier("CH0")
                    CardCarrier("CH1")
                }

            }

        }
    }
}



