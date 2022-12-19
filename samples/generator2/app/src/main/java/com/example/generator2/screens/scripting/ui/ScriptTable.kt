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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.generator2.*
import com.example.generator2.R
import com.example.generator2.screens.mainscreen4.TemplateButtonBottomBar
import com.example.generator2.screens.mainscreen4.TemplateButtonBottomBarAndLottie
import com.example.generator2.screens.ConsoleLogDraw
import com.example.generator2.screens.scripting.dialog.DialogDeleteRename
import com.example.generator2.screens.scripting.dialog.DialogSaveAs
import com.example.generator2.screens.scripting.ui.ScriptConsole
import com.example.generator2.vm.StateCommandScript
import com.example.generator2.screens.mainscreen4.VMMain4
import com.example.generator2.screens.scripting.VMScripting
import java.util.*

val refresh = mutableStateOf(0)

private val files: MutableList<String> = mutableListOf()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScriptTable(vm: VMScripting) {

    var filename by remember { mutableStateOf("") }  //Имя выбранного файла в списке

    Box(modifier = Modifier.fillMaxSize(1f)) {
        Column() {
            Row( modifier = Modifier.fillMaxSize().weight(1f) )
            {
                Box( modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.BottomEnd
                ) {
                    if (vm.hub.script.pc_ex > vm.hub.script.list.lastIndex) vm.hub.script.pc_ex =
                        vm.hub.script.list.lastIndex

                    ScriptConsole(vm.hub.script.list, vm.hub.script.pc_ex, global = vm)
                    Text(text = "PC:${vm.hub.script.pc_ex}", color = Color.Red)
                }

                Box(
                    modifier = Modifier.fillMaxHeight().width(160.dp).background(Color.LightGray), contentAlignment = Alignment.TopCenter )
                {

                    Column(
                        modifier = Modifier.fillMaxHeight().background(Color.DarkGray), verticalArrangement = Arrangement.SpaceEvenly )
                    {

                        if (vm.hub.script.state != StateCommandScript.ISEDITTING) {

                            //Кнопка New
                            TemplateButtonBottomBar(str = "New",   onClick = { vm.bNewClick() })
                            TemplateButtonBottomBar( str = "Edit", onClick = { vm.bEditClick() } )

                            // Создать список названий файлов из папки /Script
                            if (vm.hub.script.state == StateCommandScript.ISTOPPING) {
                                println("Читаем файлы")
                                files.clear()
                                files.addAll(vm.hub.utils.filesInDirToList( "/Script" ).map { it.dropLast(3) }) //
                            }

                            //Отображение списка названия скриптов
                            Column(
                                Modifier.fillMaxSize().weight(1f).padding(4.dp).background(Color(0x8B1D1C1C)).border(1.dp, Color.DarkGray).verticalScroll(rememberScrollState())
                            ) {
                                Spacer(modifier = Modifier.height(4.dp))
                                for (index in files.indices) {
                                    Text(
                                        text = files[index], color = Color.DarkGray,
                                        modifier = Modifier.fillMaxWidth().height(32.dp).padding(start = 8.dp, top = 4.dp, end = 4.dp).clip(RoundedCornerShape(8.dp)).background(Color.LightGray)
                                            .combinedClickable(
                                                onClick = {

                                                vm.hub.script.command(StateCommandScript.STOP)
                                                val l = vm.hub.utils.readScriptFileToList(files[index])
                                                vm.hub.script.list.clear()
                                                vm.hub.script.list.addAll(l)

                                            }, onLongClick = {

                                                vm.openDialogDeleteRename.value = true
                                                filename = files[index]

                                            }).offset(0.dp, (0).dp), fontFamily = FontFamily(Font(R.font.jetbrains)), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 18.sp
                                    )
                                }
                            } //

                            //Текущее состояние
                            Text( text = vm.hub.script.stateToString(), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 14.sp, color = Color.LightGray )
                            //Консоль Логов
                            ConsoleLogDraw(Modifier.weight(0.4f))
                        }

                        if (vm.hub.script.state == StateCommandScript.ISEDITTING) {
                            TemplateButtonBottomBar( modifier = Modifier.height(50.dp), str = "Back",    onClick = { vm.hub.script.command(StateCommandScript.STOP) })
                            TemplateButtonBottomBar( modifier = Modifier.height(50.dp), str = "Save",    onClick = { vm.bSaveClick() })
                            TemplateButtonBottomBar( modifier = Modifier.height(50.dp), str = "Save As", onClick = { vm.openDialogSaveAs.value = true })
                            TemplateButtonBottomBar( modifier = Modifier.height(50.dp), str = "Add",     onClick = { vm.bAddClick() })
                            TemplateButtonBottomBar( modifier = Modifier.height(50.dp), str = "Add END", onClick = { vm.bAddEndClick() })
                            TemplateButtonBottomBar( modifier = Modifier.height(50.dp), str = "Delete",  onClick = { vm.bDeleteClick() })
                            TemplateButtonBottomBar( modifier = Modifier.height(50.dp), str = "Up",      onClick = { vm.bUpClick() })
                            TemplateButtonBottomBar( modifier = Modifier.height(50.dp), str = "Down",    onClick = { vm.bDownClick() })
                        }
                    }
                }
            }

            if (vm.openDialogSaveAs.value)       DialogSaveAs(vm)
            if (vm.openDialogDeleteRename.value) DialogDeleteRename( filename, vm)

            if (vm.hub.script.state == StateCommandScript.ISEDITTING) {
                Column(
                ) {
                    vm.hub.keyboard.Core()
                }
            }
        }
    }
}
