package com.example.generator2.scripting.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.generator2.*
import com.example.generator2.R
import com.example.generator2.mainscreen4.TemplateButtonBottomBar
import com.example.generator2.mainscreen4.TemplateButtonBottomBarAndLottie
import com.example.generator2.screens.DialogDeleteRename
import com.example.generator2.screens.DialogSaveAs
import com.example.generator2.scripting.StateCommandScript
import libs.MToast
import java.util.*

val refresh = mutableStateOf(0)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScriptTable() {

    val openDialogSaveAs = remember { mutableStateOf(false) }

    val openDialogDeleteRename = remember { mutableStateOf(false) }

    val context = LocalContext.current

    var filename by remember { mutableStateOf("") }  //Имя выбранного файла в списке

    val files = remember { mutableStateListOf<String>() }


    val composition1 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.delete))
    var nonce by remember { mutableStateOf(1) }
    val animatable = rememberLottieAnimatable()

    LaunchedEffect(composition1, nonce) {
        composition1 ?: return@LaunchedEffect
        animatable.animate(
            composition1,
            continueFromPreviousAnimate = false,
        )
    }

    LottieAnimation(composition1,
        { animatable.progress },
        modifier = Modifier.clickable { nonce++ })



    Box(modifier = Modifier.fillMaxSize(1f)) {

        //refresh.value = refresh.value

        Column() {

            Row(
                modifier = Modifier.fillMaxSize().weight(1f)
            ) {

                Box(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    contentAlignment = Alignment.BottomEnd
                ) {

                    if (Global.script.pc.value > Global.script.list.lastIndex) Global.script.pc.value =
                        Global.script.list.lastIndex

                    ScriptConsole(Global.script.list, Global.script.pc.value)

                    Text(text = "PC:${Global.script.pc.value}", color = Color.Red)
                }

                Box(
                    modifier = Modifier.fillMaxHeight().width(160.dp).background(Color.LightGray),
                    contentAlignment = Alignment.TopCenter
                ) {

                    Column(
                        modifier = Modifier.fillMaxHeight().background(Color.DarkGray),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        if (Global.script.state != StateCommandScript.ISEDITTING) {

                            TemplateButtonBottomBar(str = "New", onClick = {

                                Global.script.command(StateCommandScript.STOP)
                                Global.script.list.clear()
                                Global.script.list.add("New")
                                Global.script.list.add("?")
                                Global.script.list.add("END")

                                Global.script.command(StateCommandScript.EDIT)

                            })
                            TemplateButtonBottomBarAndLottie(str = "Edit", onClick = {
                                Global.script.command(StateCommandScript.EDIT)
                            }, resId = R.raw.paper_notebook_writing_animation, autostart = true) //

                            files.clear()
                            files.addAll(filesInDirToList(
                                context, "/Script"
                            ).map { it.dropLast(3) })

                            Column(
                                Modifier.fillMaxSize().weight(1f).padding(4.dp)
                                    .background(Color(0x8B1D1C1C)).border(1.dp, Color.DarkGray)
                                    .verticalScroll(rememberScrollState())
                            ) {

                                Spacer(modifier = Modifier.height(4.dp))
                                for (index in files.indices) {
                                    Text(
                                        text = files[index],
                                        color = Color.DarkGray,
                                        modifier = Modifier.fillMaxWidth().height(32.dp)
                                            .padding(start = 8.dp, top = 4.dp, end = 4.dp).clip(
                                                RoundedCornerShape(8.dp)
                                            ).background(Color.LightGray)
                                            .combinedClickable(onClick = {
                                                Global.script.command(StateCommandScript.STOP)
                                                val l = readScriptFileToList(files[index])
                                                Global.script.list.clear()
                                                Global.script.list.addAll(l)
                                            }, onLongClick = {
                                                openDialogDeleteRename.value = true
                                                filename = files[index]
                                            }).offset(0.dp, (0).dp),
                                        fontFamily = FontFamily(Font(R.font.jetbrains)),
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        fontSize = 18.sp
                                    )
                                }
                            } //

                            //TemplateButtonBottomBar(str = Global.script.StateToString())

                            Text(
                                text = Global.script.StateToString(),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp
                            )

                            //Консоль Логов
                            ConsoleLogDraw(Modifier.weight(0.4f))


                        }





                        if (Global.script.state == StateCommandScript.ISEDITTING) {

                            TemplateButtonBottomBarAndLottie(modifier = Modifier.height(50.dp),
                                str = "Назад",
                                onClick = { Global.script.command(StateCommandScript.STOP) },
                                resId = R.raw.back
                            )

                            TemplateButtonBottomBarAndLottie(
                                modifier = Modifier.height(50.dp),
                                str = "Save",
                                onClick = {
                                    if (Global.script.list[0] == "New") openDialogSaveAs.value =
                                        true
                                    else {
                                        saveListToScriptFile(
                                            Global.script.list,
                                            Global.script.list[0]
                                        )
                                        MToast(contex = Global.contextActivity!!, "Сохранено")
                                    }
                                },

                                resId = R.raw.lf30_editor_loddotda,
                                iterationsInfitity = true,
                                autostart = true,
                                size = 26.dp,
                                paddingStart = 5.dp,
                                paddingStartText = 12.dp

                            )

                            TemplateButtonBottomBarAndLottie(
                                modifier = Modifier.height(50.dp),
                                str = "Save As",
                                onClick = {
                                    openDialogSaveAs.value = true
                                },

                                resId = R.raw.lf30_editor_loddotda,
                                iterationsInfitity = false,
                                size = 26.dp,
                                paddingStart = 5.dp,
                                paddingStartText = 12.dp
                            )

                            TemplateButtonBottomBar(
                                modifier = Modifier.height(50.dp),
                                str = "Add",
                                onClick = {
                                    Global.script.list.add(Global.script.pc.value + 1, "?")
                                })
                            
                            TemplateButtonBottomBarAndLottie(

                                modifier = Modifier.height(50.dp),

                                str = "Add END",
                                onClick = {
                                    Global.script.list.add(Global.script.pc.value + 1, "END")
                                },

                                resId = R.raw.add2,
                                iterationsInfitity = false,
                                size = 50.dp,
                                paddingStart = 5.dp,
                                paddingStartText = 12.dp

                            )


                            TemplateButtonBottomBarAndLottie(
                                modifier = Modifier.height(50.dp), str = "Delete",
                                onClick = {

                                    if (Global.script.list.size > 1) {

                                        Global.script.list.removeAt(Global.script.pc.value)

                                        //if (pc.value > 1)
                                        //  pc.value--

                                        if (Global.script.pc.value > Global.script.list.lastIndex) {
                                            Global.script.pc.value = Global.script.list.lastIndex
                                        }
                                    }

                                },
                                resId = R.raw.delete, size = 50.dp,
                            )





                            TemplateButtonBottomBarAndLottie(
                                modifier = Modifier.height(50.dp), str = "Up",
                                onClick = {
                                    if (Global.script.pc.value > 1) {
                                        Collections.swap(
                                            Global.script.list,
                                            Global.script.pc.value - 1,
                                            Global.script.pc.value
                                        )
                                        Global.script.pc.value--
                                    }
                                },

                                resId = R.raw.up,
                                size = 50.dp,


                                )

                            TemplateButtonBottomBarAndLottie(
                                modifier = Modifier.height(50.dp), str = "Down",
                                onClick = {
                                    if ((Global.script.pc.value > 0) && (Global.script.pc.value < Global.script.list.lastIndex)) {
                                        Collections.swap(
                                            Global.script.list,
                                            Global.script.pc.value + 1,
                                            Global.script.pc.value
                                        )
                                        Global.script.pc.value++
                                    }
                                },
                                resId = R.raw.down,
                                size = 50.dp,
                            )


                            //TemplateButtonBottomBar(str = StateToString())

                        }


                    }
                }
            }

            DialogSaveAs(openDialogSaveAs)

            DialogDeleteRename(openDialogDeleteRename, filename)



            if (Global.script.state == StateCommandScript.ISEDITTING) {
                Column(
                ) {
                    Global.keyboard.Core()
                }
            }
        }
    }
}


