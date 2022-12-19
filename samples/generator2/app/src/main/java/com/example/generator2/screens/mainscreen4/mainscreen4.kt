package com.example.generator2.screens.mainscreen4

import CardCarrier
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.generator2.screens.mainscreen4.ui.CardCommander
import com.example.generator2.screens.mainscreen4.ui.DrawerContentBottom
import com.example.generator2.screens.mainscreen4.ui.M4BottomAppBarComponent
import com.example.generator2.theme.colorDarkBackground
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun mainsreen4(
    navController: NavHostController, global: VMMain4
) {

    val coroutineScope = rememberCoroutineScope()
    val drawerState: BottomDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val openDrawer: () -> Unit = { coroutineScope.launch { drawerState.expand() } }
    val closeDrawer: () -> Unit = { coroutineScope.launch { drawerState.close() } }

    val toggleDrawer: () -> Unit = {
        if (drawerState.isOpen) {
            closeDrawer()
        } else {
            openDrawer()
        }
    }

    Scaffold(
        //Нижняя панель
        bottomBar = { M4BottomAppBarComponent(toggleDrawer, navController, global) }

    )
    {

        BottomDrawer(gesturesEnabled = drawerState.isOpen,
            drawerState = drawerState,
            drawerContent = {

                Box(
                    modifier = Modifier
                        .padding(bottom = it.calculateBottomPadding())
                        .background(Color(0xFF242323))
                )
                {
                    DrawerContentBottom(global) //Список устройств
                }

            },
            content = {


                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding())
                            .background(colorDarkBackground)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CardCarrier("CH0", global)
                        CardCommander()
                        CardCarrier("CH1", global)
                    }




            })
    }
}
