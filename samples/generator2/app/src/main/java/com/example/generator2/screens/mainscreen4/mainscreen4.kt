package com.example.generator2.screens.mainscreen4

import CardCarrier
import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.generator2.screens.mainscreen4.ui.DrawerContentBottom
import com.example.generator2.screens.mainscreen4.ui.M4BottomAppBarComponent
import com.example.generator2.theme.colorDarkBackground
import com.example.generator2.vm.LiveData
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
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

            }
        ) {

            val mono by LiveData.mono.collectAsState()



            val animateHeight by animateDpAsState(
                    targetValue = if (!mono) 314.dp  else 0.dp,
                animationSpec = tween(durationMillis = 250)
            )

            val animateAlpha by animateFloatAsState(
                targetValue = if (!mono) 1f else 0.0f,
                animationSpec = tween(durationMillis = 250)
            )

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding())
                    .background(colorDarkBackground)
                    .verticalScroll(rememberScrollState()),
                //verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(1f)
                    //.background(Color.Green)
                        )


                Box()
                {
                    CardCarrier("CH0", global)
                }
                Spacer(modifier = Modifier.height(8.dp))
                CardCommander(global)
                Spacer(modifier = Modifier.height(8.dp))

                    Box(modifier = Modifier
                        .height(animateHeight)
                        .graphicsLayer(
                            alpha = animateAlpha,
                            //scaleY = animateAlpha
                            translationY = 500f*(1f-animateAlpha), clip = true
                        )
                    )
                    {
                        CardCarrier("CH1", global)
                    }








                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(1f)
                    //.background(Color.Red)
                )
            }
        }
    }
}
