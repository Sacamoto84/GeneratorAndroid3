package com.example.generator2.screens.mainscreen4

import CardCarrier
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import colorDarkBackground
import colorLightBackground
import com.example.generator2.R
import com.example.generator2.vm.Global
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


//var bottomBarRoute : bottomBarEnum = bottomBarEnum.HOME

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun mainsreen4(
    navController: NavHostController, global: Global
) {


    val coroutineScope = rememberCoroutineScope()
    val drawerState: BottomDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val openDrawer: () -> Unit = { coroutineScope.launch { drawerState.expand() } }
    val closeDrawer: () -> Unit = { coroutineScope.launch { drawerState.close() } }
    var selectedBottomDrawerIndex by remember { mutableStateOf(0) }

    val toggleDrawer: () -> Unit = {
        if (drawerState.isOpen) {
            closeDrawer()
        } else {
            openDrawer()
        }
    }


    Scaffold(isFloatingActionButtonDocked = false,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBarComponent(toggleDrawer, navController)
        }) {

        BottomDrawer(gesturesEnabled = drawerState.isOpen,
            drawerState = drawerState, // scrimColor color of the scrim that obscures content when the drawer is open. If the
            // color passed is [androidx.compose.ui.graphics.Color.Unspecified],
            // then a scrim will no longer be applied and the bottom
            //        scrimColor = Color.Unspecified,
            drawerContent = {

                DrawerContentBottom()

            },
            content = { // Select user from list in main screen and send it to BottomDrawer via this lambda
                Column(
                    Modifier.fillMaxSize().padding(bottom = it.calculateBottomPadding())
                        .verticalScroll(rememberScrollState()).background(colorDarkBackground),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    CardCarrier("CH0", global)
                    CardCarrier("CH1", global)
                }
            })


    }


    //    Scaffold(
    //        bottomBar = {
    //            when ( global.bottomBarRoute.value) {
    //                bottomBarEnum.HOME -> BottomBarGenerator()
    //                bottomBarEnum.SAVE -> BottomBarSave()
    //                bottomBarEnum.SAVEAS -> BottomBarSave()
    //                bottomBarEnum.LOAD -> BottomBarLoad()
    //                bottomBarEnum.LOADAS -> BottomBarLoad()
    //                bottomBarEnum.SCRIPT -> bottomBarScript()
    //                bottomBarEnum.EDITOR -> bottomBarEditor()
    //
    //                else -> BottomBarGenerator()
    //            }
    //        },
    //        backgroundColor = colorDarkBackground
    //    )
    //    {
    //
    //        when ( global.bottomBarRoute.value) {
    //
    //            bottomBarEnum.HOME -> {
    //                Column(
    //                    Modifier
    //                        .fillMaxSize()
    //                        .padding(bottom = it.calculateBottomPadding())
    //                        .verticalScroll(rememberScrollState()),
    //                    verticalArrangement = Arrangement.SpaceEvenly
    //                ) {
    //                    CardCarrier("CH0")
    //                    CardCarrier("CH1")
    //                }
    //            }
    //
    //            bottomBarEnum.SAVEAS -> {
    //
    //                Box(
    //                    modifier = Modifier.padding(bottom = it.calculateBottomPadding())
    //                        .fillMaxSize()
    //                        .background(Color.Red)
    //                )
    //                {
    //                    Text("SAVEAS")
    //                }
    //
    //            }
    //
    //            bottomBarEnum.LOADAS -> {
    //
    //                Box(
    //                    modifier = Modifier.padding(bottom = it.calculateBottomPadding())
    //                        .fillMaxSize()
    //                        .background(Color.Green)
    //
    //                )
    //                {
    //                    Text("LOADAS")
    //                }
    //
    //            }
    //
    //            bottomBarEnum.SCRIPT -> {
    //                Box(
    //                    modifier = Modifier.padding(bottom = it.calculateBottomPadding())
    //                        .fillMaxSize()
    //                )
    //                {
    //                    ScreenScriptCommon()
    //                }
    //            }
    //
    //            bottomBarEnum.EDITOR -> {
    //                Box(
    //                    modifier = Modifier.padding(bottom = it.calculateBottomPadding())
    //                        .fillMaxSize()
    //                )
    //                {
    //                    ScreenEditor()
    //                }
    //            }
    //
    //
    //
    //            else -> {
    //                CardCarrier("CH0")
    //                CardCarrier("CH1")
    //            }
    //
    //        }
    //
    //
    //    }

}


val bottomDrawerList = listOf(
    Pair("Inbox", Icons.Filled.Favorite),
    Pair("Outbox", Icons.Filled.AccountBox),
    Pair("Favorites", Icons.Filled.Favorite),
    Pair("Archive", Icons.Filled.Favorite),
    Pair("Trash", Icons.Filled.Delete),
)


/**
 * Заполняем Drawer
 * Drawer content for [BottomDrawer]
 */
@Composable
fun DrawerContentBottom(

) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .heightIn(min = 100.dp, max = 600.dp) //            .height(500.dp)
            //            .fillMaxSize()
            .padding(8.dp)
    ) {

        Column(modifier = Modifier.padding(8.dp)) {
            Text("Mail", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) { //Text(text = "$selectedUser@abc.com")

            }
        }

        bottomDrawerList.forEachIndexed { index, pair ->
            val label = pair.first
            val imageVector = pair.second
            DrawerButton(icon = imageVector,
                label = label,
                isSelected = false,
                action = { //onSelected(index)
                })
        }
    }
}


//Нижняя панель с кнопками
@Composable
private fun BottomAppBarComponent(toggleDrawer: () -> Unit, navController: NavHostController) {
    BottomAppBar(
        backgroundColor = colorLightBackground,
        contentColor = Color.White,
        elevation = 2.dp,
        cutoutShape = CircleShape
    ) {

        // Leading icons should typically have a high content alpha
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            IconButton(
                onClick = toggleDrawer
            ) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }

        } // The actions should be at the end of the BottomAppBar. They use the default medium
        // content alpha provided by BottomAppBar
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = {navController.navigate("script") }) {
            Icon(painter = painterResource(R.drawable.script3), contentDescription = null)
        }

        Spacer(modifier = Modifier.weight(0.1f))

        IconButton(onClick = {navController.navigate("editor") }) {
            Icon(painter = painterResource(R.drawable.editor), contentDescription = null)
        }

        Spacer(modifier = Modifier.weight(0.2f))

        IconButton(onClick = { exitProcess(0) }) {
            Icon(painter = painterResource(R.drawable.close), contentDescription = null)
        }
        //Spacer(modifier = Modifier.weight(0.1f))

    }
}


/**
 * Button for ModalDrawer or BottomDrawer menu items
 */
@Composable
fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val imageAlpha = if (isSelected) {
        1f
    } else {
        0.8f
    }
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.9f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    val surfaceModifier = modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp).fillMaxWidth()
    Surface(
        modifier = surfaceModifier, color = backgroundColor, shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action, modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    imageVector = icon, contentDescription = null, // decorative
                    colorFilter = ColorFilter.tint(textIconColor), alpha = imageAlpha
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    fontWeight = FontWeight.Bold,
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor
                )
            }
        }
    }
}


