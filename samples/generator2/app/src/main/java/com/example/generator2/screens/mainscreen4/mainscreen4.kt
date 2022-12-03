package com.example.generator2.screens.mainscreen4

import CardCarrier
import android.annotation.SuppressLint
import android.media.Spatializer
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import colorDarkBackground
import colorLightBackground
import com.example.generator2.R
import com.example.generator2.ui.wiget.UIswitch.ON_OFF
import com.example.generator2.vm.Global
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
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


        BottomDrawer(
            //gesturesEnabled = drawerState.isOpen,
            drawerState = drawerState,
            drawerContent = {

                Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding()).background(Color(
                    0xFF242323
                )
                )) {
                    DrawerContentBottom(global)
                }

            },
            content = { // Select user from list in main screen and send it to BottomDrawer via this lambda
                Column(
                    Modifier.fillMaxSize().padding(bottom = it.calculateBottomPadding())
                        .verticalScroll(rememberScrollState()).background(colorDarkBackground),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    //ON_OFF(true, global.onoffconfig, {})

                    CardCarrier("CH0", global)
                    CardCarrier("CH1", global)
                }
            })


    }

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
    global: Global

) {



//    Column(
//        modifier = Modifier.fillMaxWidth()
//            //.heightIn(max = 80000.dp) //
//            //.wrapContentHeight()
//            //            .fillMaxSize()
//            .padding(8.dp).background(Color.Black)
//    ) {


//        Column(modifier = Modifier.padding(8.dp)) {
//            Text("Audio Devices", fontWeight = FontWeight.Bold, fontSize = 20.sp)
//
//            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) { //Text(text = "$selectedUser@abc.com")
//
//            }
//        }


    Column(
        modifier = Modifier
            //.fillMaxHeight(0.7f)
            .fillMaxWidth()
    ) {


        Text("Audio Devices", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(8.dp))

    //Получить список устройств
    global.audioDevice.mDeviceAdapter.forEachIndexed { index, pair ->

        val label = pair.id.toString() + " " + pair.name.toString()

        var imageVector: Painter = painterResource(R.drawable.info)

        val str = pair.name.toString()

        if (str.indexOf("earphone") != -1) imageVector = painterResource(R.drawable.earphone)

        if (str.indexOf("built-in speaker") != -1) imageVector =
            painterResource(R.drawable.speaker2)

        if (str.indexOf("headphones") != -1) imageVector = painterResource(R.drawable.headphones)

        if (str.indexOf("Bluetooth") != -1) imageVector = painterResource(R.drawable.headset)

        if (str.indexOf("A2DP") != -1) imageVector = painterResource(R.drawable.bluetooth)

        if (str.indexOf("Auto select") != -1) imageVector = painterResource(R.drawable.auto2)

        DrawerButton(icon = imageVector, label = label, action = {

            GlobalScope.launch(Dispatchers.Main) {
                global.playbackEngine.stop()

                global.playbackEngine.delete()
                global.playbackEngine.create()

                global.audioDevice.OnItemSelectedListener(index)
                global.playbackEngine.start()
                delay(1000)
                global.sendAlltoGen()
            }

        })


    }

        Spacer(modifier = Modifier.height(8.dp))


}


    //}
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

        IconButton(onClick = { navController.navigate("script") }) {
            Icon(painter = painterResource(R.drawable.script3), contentDescription = null)
        }

        Spacer(modifier = Modifier.weight(0.1f))

        IconButton(onClick = { navController.navigate("editor") }) {
            Icon(painter = painterResource(R.drawable.editor), contentDescription = null)
        }


        Spacer(modifier = Modifier.weight(0.2f))

        //        IconButton(onClick = { exitProcess(0) }) {
        //            Icon(painter = painterResource(R.drawable.close), contentDescription = null)
        //        }
        //        IconButton(onClick = { exitProcess(0) }) {
        //            Icon(painter = painterResource(R.drawable.close2), contentDescription = null)
        //        }
        //        IconButton(onClick = { exitProcess(0) }) {
        //            Icon(painter = painterResource(R.drawable.close3), contentDescription = null)
        //        }
        IconButton(onClick = { exitProcess(0) }) {
            Icon(painter = painterResource(R.drawable.close4), contentDescription = null)
        }

        //Spacer(modifier = Modifier.weight(0.1f))

    }
}

/**
 * Button for ModalDrawer or BottomDrawer menu items
 */
@Composable
fun DrawerButton(
    icon: Painter,
    label: String,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {

    val surfaceModifier = modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp).fillMaxWidth()
    Surface(
        modifier = surfaceModifier, color = Color.DarkGray, shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action, modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {


                Icon(painter = icon, contentDescription = null,  tint = Color.White, modifier = Modifier.size(24.dp))

                Spacer(Modifier.width(16.dp))
                Text(
                    fontWeight = FontWeight.Bold,
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
            }
        }
    }




}


