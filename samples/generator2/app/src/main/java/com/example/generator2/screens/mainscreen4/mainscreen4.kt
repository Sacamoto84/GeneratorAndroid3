package com.example.generator2.screens.mainscreen4

import CardCarrier
import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.generator2.screens.mainscreen4.ui.DrawerContentBottom
import com.example.generator2.screens.mainscreen4.ui.M4BottomAppBarComponent
import com.example.generator2.theme.colorDarkBackground
import com.example.generator2.data.LiveData
import com.example.generator2.screens.mainscreen4.card.CardCommander
import com.example.generator2.screens.mainscreen4.vm.VMMain4
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun mainsreen4(
    navController: NavHostController, vm: VMMain4
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
        bottomBar = { M4BottomAppBarComponent(toggleDrawer, navController, vm) }          //<-- Нижняя панель
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
                    DrawerContentBottom(vm) //Список устройств
                }

            }
        ) {

            val mono by LiveData.mono.collectAsState()

//            val animateHeight by animateDpAsState(
//                targetValue = if (!mono) 314.dp else 0.dp,
//                animationSpec = tween(durationMillis = 7050)
//            )
//
//            val animateAlpha by animateFloatAsState(
//                targetValue = if (!mono) 1f else 0.0f,
//                animationSpec = tween(durationMillis = 7050)
//            )

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding())
                    .background(colorDarkBackground)
                    .verticalScroll(rememberScrollState()),
                //verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().weight(1f))

                Column()
                {
                    CardCarrier("CH0")
                    Spacer(modifier = Modifier.height(8.dp))
                    CardCommander(vm)                                                     //<-- Commander
                    Spacer(modifier = Modifier.height(8.dp))
                }

                AnimatedContent(
                    targetState = mono,
                    transitionSpec = {
                        val time = 400
                        //Появление
                        (fadeIn(animationSpec = tween(time / 2)) + expandVertically( animationSpec = tween( time )))
                            .with( (fadeOut(animationSpec = tween(time)) + shrinkVertically( animationSpec = tween(time))) ).using(
                                SizeTransform( clip = true, sizeAnimationSpec = { _, _ -> tween(time) }))
                    }
                )
                {
                    if (!it) CardCarrier("CH1") else   Spacer(modifier = Modifier.height(1.dp).fillMaxWidth())
                }

                Box( modifier = Modifier.fillMaxHeight().fillMaxWidth().weight(1f) )

            }
        }
    }
}
