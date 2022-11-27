package com.example.generator2.mainscreen4

import BottomBarSave
import CardCarrier
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import bottomBarEditor
import colorDarkBackground
import com.example.generator2.Global
import com.example.generator2.editor.ScreenEditor
import com.example.generator2.screens.ScreenScriptCommon


//var bottomBarRoute : bottomBarEnum = bottomBarEnum.HOME

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun mainsreen4(
    global: Global = viewModel()
) {
    Scaffold(
        bottomBar = {
            when ( global.bottomBarRoute.value) {
                bottomBarEnum.HOME -> BottomBarGenerator()
                bottomBarEnum.SAVE -> BottomBarSave()
                bottomBarEnum.SAVEAS -> BottomBarSave()
                bottomBarEnum.LOAD -> BottomBarLoad()
                bottomBarEnum.LOADAS -> BottomBarLoad()
                bottomBarEnum.SCRIPT -> bottomBarScript()
                bottomBarEnum.EDITOR -> bottomBarEditor()

                else -> BottomBarGenerator()
            }
        },
        backgroundColor = colorDarkBackground
    )
    {

        when ( global.bottomBarRoute.value) {

            bottomBarEnum.HOME -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(bottom = it.calculateBottomPadding())
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    CardCarrier("CH0")
                    CardCarrier("CH1")
                }
            }

            bottomBarEnum.SAVEAS -> {

                Box(
                    modifier = Modifier.padding(bottom = it.calculateBottomPadding())
                        .fillMaxSize()
                        .background(Color.Red)
                )
                {
                    Text("SAVEAS")
                }

            }

            bottomBarEnum.LOADAS -> {

                Box(
                    modifier = Modifier.padding(bottom = it.calculateBottomPadding())
                        .fillMaxSize()
                        .background(Color.Green)

                )
                {
                    Text("LOADAS")
                }

            }

            bottomBarEnum.SCRIPT -> {
                Box(
                    modifier = Modifier.padding(bottom = it.calculateBottomPadding())
                        .fillMaxSize()
                )
                {
                    ScreenScriptCommon()
                }
            }

            bottomBarEnum.EDITOR -> {
                Box(
                    modifier = Modifier.padding(bottom = it.calculateBottomPadding())
                        .fillMaxSize()
                )
                {
                    ScreenEditor()
                }
            }



            else -> {
                CardCarrier("CH0")
                CardCarrier("CH1")
            }

        }


    }
}



