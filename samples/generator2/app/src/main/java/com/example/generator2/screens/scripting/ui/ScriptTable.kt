package com.example.generator2.screens.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.generator2.*
import com.example.generator2.R
import com.example.generator2.screens.mainscreen4.TemplateButtonBottomBar
import com.example.generator2.screens.mainscreen4.TemplateButtonBottomBarAndLottie
import com.example.generator2.screens.ConsoleLogDraw
import com.example.generator2.screens.scripting.dialog.DialogDeleteRename
import com.example.generator2.screens.scripting.dialog.DialogSaveAs
import com.example.generator2.screens.scripting.ui.ScriptConsole
import com.example.generator2.vm.StateCommandScript
import com.example.generator2.vm.Global
import libs.MToast
import java.util.*

val refresh = mutableStateOf(0)

private val files : MutableList<String> = mutableListOf()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScriptTable(global: Global) {


    val openDialogSaveAs = remember { mutableStateOf(false) }
    val openDialogDeleteRename = remember { mutableStateOf(false) }

    //val context = LocalContext.current

    var filename by remember { mutableStateOf("") }  //Имя выбранного файла в списке

    //val files =  mutableStateListOf<String>()

    //val composition1 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.delete))

    //var nonce by remember { mutableStateOf(1) }
    //val animatable = rememberLottieAnimatable()


    //    LaunchedEffect(composition1, nonce) {
    //        composition1 ?: return@LaunchedEffect
    //        animatable.animate(
    //            composition1,
    //            continueFromPreviousAnimate = false,
    //        )
    //    }

    //    LottieAnimation(composition1,
    //        { animatable.progress },
    //        modifier = Modifier.clickable { nonce++ })




    Box(modifier = Modifier.fillMaxSize(1f)) {
          Column() {
              Row(
                modifier = Modifier.fillMaxSize().weight(1f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    contentAlignment = Alignment.BottomEnd
                ) {

                    if (global.script.pc_ex > global.script.list.lastIndex) global.script.pc_ex =
                        global.script.list.lastIndex

                    ScriptConsole(global.script.list, global.script.pc_ex, global = global)

                    Text(text = "PC:${global.script.pc_ex}", color = Color.Red)
                }

                Box(
                    modifier = Modifier.fillMaxHeight().width(160.dp).background(Color.LightGray),
                    contentAlignment = Alignment.TopCenter
                ) {

                    Column(
                        modifier = Modifier.fillMaxHeight().background(Color.DarkGray),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        if (global.script.state != StateCommandScript.ISEDITTING) {

                            //Кнопка New
                            TemplateButtonBottomBar(str = "Новый", onClick = {

                                global.script.command(StateCommandScript.STOP)
                                global.script.list.clear()
                                global.script.list.add("New")
                                global.script.list.add("?")
                                global.script.list.add("END")

                                global.script.command(StateCommandScript.EDIT)

                            })

                            TemplateButtonBottomBarAndLottie(
                                str = "Изменить", onClick = {
                                    global.script.command(StateCommandScript.EDIT)
                                }, resId = R.raw.paper_notebook_writing_animation, autostart = false
                            ) //

                            // Создать список названий файлов из папки /Script
                            if (global.script.state == StateCommandScript.ISTOPPING) {
                                println("Читаем файлы")
                                files.clear()
                                files.addAll(global.utils.filesInDirToList(
                                    "/Script"
                                ).map { it.dropLast(3) }) //
                            }

                            //Отображение списка названия скриптов
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
                                                global.script.command(StateCommandScript.STOP)
                                                val l =
                                                    global.utils.readScriptFileToList(files[index])
                                                global.script.list.clear()
                                                global.script.list.addAll(l)
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

                            //Текущее состояние
                            Text(
                                text = global.script.stateToString(),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.LightGray
                            )

                            //Консоль Логов
                            ConsoleLogDraw(Modifier.weight(0.4f))


                        }

                        if (global.script.state == StateCommandScript.ISEDITTING) {

                            TemplateButtonBottomBarAndLottie(
                                modifier = Modifier.height(50.dp),
                                str = "Назад",
                                onClick = { global.script.command(StateCommandScript.STOP) },
                                resId = R.raw.back
                            )

                            TemplateButtonBottomBarAndLottie(
                                modifier = Modifier.height(50.dp),
                                str = "Save",
                                onClick = {
                                    if (global.script.list[0] == "New") openDialogSaveAs.value =
                                        true
                                    else {
                                        global.utils.saveListToScriptFile(
                                            global.script.list, global.script.list[0]
                                        )
                                        //MToast(contex = global.contextActivity!!, "Сохранено")
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
                                    global.script.list.add(global.script.pc + 1, "?")
                                    global.script.pc_ex = global.script.pc
                                })

                            TemplateButtonBottomBarAndLottie(

                                modifier = Modifier.height(50.dp),

                                str = "Add END",
                                onClick = {
                                    global.script.list.add(global.script.pc + 1, "END")
                                    global.script.pc_ex = global.script.pc
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

                                    if (global.script.list.size > 1) {

                                        global.script.list.removeAt(global.script.pc)

                                        if (global.script.pc > global.script.list.lastIndex) {
                                            global.script.pc = global.script.list.lastIndex
                                        }

                                        global.script.pc_ex = global.script.pc
                                    }

                                },
                                resId = R.raw.delete, size = 50.dp,
                            )

                            TemplateButtonBottomBarAndLottie(
                                modifier = Modifier.height(50.dp), str = "Up",
                                onClick = {

                                    if (global.script.pc > 1) {
                                        Collections.swap(
                                            global.script.list,
                                            global.script.pc - 1,
                                            global.script.pc
                                        )
                                        global.script.pc--
                                    }

                                    global.script.pc_ex = global.script.pc
                                },

                                resId = R.raw.up,
                                size = 50.dp,

                                )

                            TemplateButtonBottomBarAndLottie(
                                modifier = Modifier.height(50.dp), str = "Down",
                                onClick = {
                                    if ((global.script.pc > 0) && (global.script.pc < global.script.list.lastIndex)) {
                                        Collections.swap(
                                            global.script.list,
                                            global.script.pc + 1,
                                            global.script.pc
                                        )
                                        global.script.pc++
                                    }
                                    global.script.pc_ex = global.script.pc
                                },
                                resId = R.raw.down,
                                size = 50.dp,
                            )


                            //TemplateButtonBottomBar(str = StateToString())

                        }


                    }
                }
            }

            if (openDialogSaveAs.value) DialogSaveAs(openDialogSaveAs, global)

            if (openDialogDeleteRename.value) DialogDeleteRename(
                openDialogDeleteRename,
                filename,
                global
            )

            if (global.script.state == StateCommandScript.ISEDITTING) {
                Column(
                ) {
                    global.keyboard.Core(global)
                }
            }
        }
    }
}
