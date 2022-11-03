package com.example.generator2.scripting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.generator2.Global
import com.example.generator2.console.Console2
import com.example.generator2.mainscreen4.TemplateButtonBottomBar
import com.example.generator2.recomposeHighlighterOneLine
import com.example.generator2.scripting.ui.ScriptConsole
import com.example.generator2.scripting.ui.ScriptKeyboard
import kotlinx.coroutines.delay
import libs.modifier.recomposeHighlighter
import java.sql.Struct

/*
 * ----------------- Логика -----------------
 * IF R1 = 2
 * ...
 * ELSE
 * ...
 * ENDIF
 *
 * DELAY 1000 - Задержка работы
 */

/*
 *╭─ Выйти ─╮╭─ Переход ─╮╭─ Задержка ─╮╭─ Завершение ─╮╭─ Текст ────╮
 *│ YIELD   ││ GOTO 2    ││ DELAY 4000 ││ END          ││ TEXT Текст │
 *╰─────────╯╰───────────╯╰────────────╯╰──────────────╯╰────────────╯
 *╭─ Арифметика ────┬─────────────────────╮╭─ Загрузка константы в регистр ─╮
 *│ MINUS R1 5000   │ R1 - 5000-> R1      ││ LOAD R1 2344    │ 2344 -> R1   │
 *│ MINUS F1 5000.0 │ F1 - 5.1 -> F1      ││ LOAD F1 2344.0  │ 2344 -> F1   │
 *│                 │                     │╰─────────────────┴──────────────╯
 *│ MINUS R1 R2     │ R1-R2->R1           │╭─ Отобразить дамп регистров ──╮
 *│ MINUS F1 F2     │ F1-F2->F1           ││ PRINTF - Дамп регистров F    │
 *│                 │                     │╰──────────────────────────────╯
 *│ PLUS R1 4555    │ R1 + 4555 -> 4555   │
 *│ PLUS F1 4555.5  │ F1 + 4555.5 -> 4555 │
 *│                 │                     │
 *│ PLUS R1 R2      │ R1+R2->R1           │
 *│ PLUS F1 F2      │ F1+F2->F1           │
 *╰─────────────────┴─────────────────────╯
 *╭─ Генератор ─────────────────╮╭─╮╭────────────┬──────────────╮
 *│ CH[1 2] [CR AM FM] [ON OFF] ││✓││IF F1 < 450 │ IF F1 < 450  │
 *│                             ││ ││...{true}   │ ...  {true}  │
 *│ CR[1 2] FR 1000.0     F[]   ││✓││ELSE        │ ENDIF        │
 *│ CR[1 2] MOD 02_HWawe        ││✓││...{false}  │ ...  {false} │
 *│                             ││ ││ENDIF       │              │
 *│ AM[1 2] FR 1000.3     F[]   ││✓│├───┬────┬───┼────┬────┬────┤
 *│ AM[1 2] MOD 02_HWawe        ││✓││ < │ <= │ > │ >= │ == │ != │
 *│                             ││ │╰───┴────┴───┴────┴────┴────╯
 *│ FM[1 2] BASE 1234.6   F[]   ││✓│
 *│ FM[1 2] DEV  123.8    F[]   ││✓│
 *│ FM[1 2] MOD  02_HWawe       ││✓│
 *│ FM[1 2] FR   3.5      F[]   ││✓│
 *╰─────────────────────────────╯╰─╯
 * ┌ ┐ └ ┘├ ┤ ┬ ┴ ┼ ─ │╭╮╯╰│ ─ ✓
 *╭─ Копирование регистров ───╮ Не готово
 *│ COPY R1 R2       │ R2->R1 │
 *│ COPY F1 F2       │ F2->F1 │
 *╰──────────────────┴────────┘
 *
 *
 * Переход если равно и не равно
 *
 *
 */

const val PC_MAX = 128 //Максимальный размер скрипта..При динамическом списке не имеет смысла

//Экраны для нижнего меню
enum class StateCommandScript {
    IDLE,
    START,
    PAUSE,
    RESUME,
    STOP,
    EDIT, //Перевести в режим редактирования

    //Состояния
    isRUNNUNG,
    isSTOPING,
    isEDITTING, //Сейчас режим редактирования
}

//Основной класс скриптовой системы
class Script {

    //╭─ Генератор ───────────────────────╮
    var scriptName: String = ""    //Имя текущего скрипта

    var end = true
    var yield = false
    var endTime = 0L              //Время > которого можно продолжать работу

    var f = mutableStateListOf<Float>()
    var pc = mutableStateOf(0)
    var str: String = ""
    var list = mutableStateListOf<String>()
    var state by mutableStateOf(StateCommandScript.isSTOPING)

    init {
        f.addAll(FloatArray(10).toList())
    }

    fun command(s: StateCommandScript) {

        when (s) {
            StateCommandScript.STOP -> {
                stop()
                state = StateCommandScript.isSTOPING
            }
            StateCommandScript.PAUSE -> {
                pause()
                state = StateCommandScript.isSTOPING
            }
            StateCommandScript.RESUME -> {
                resume()
                state = StateCommandScript.isRUNNUNG
            }

            StateCommandScript.START -> {
                start()
                state = StateCommandScript.isRUNNUNG
            }

            StateCommandScript.EDIT -> {
                stop()
                state = StateCommandScript.isEDITTING
            }

            else -> {}
        }
    }

    //╰─────────────────────────────╯
    fun StateToString(): String {
        val s =
            when (state) {
                StateCommandScript.IDLE -> "IDLE"
                StateCommandScript.START -> "START"
                StateCommandScript.PAUSE -> "PAUSE"
                StateCommandScript.RESUME -> "RESUME"
                StateCommandScript.STOP -> "STOP"
                StateCommandScript.EDIT -> "EDIT"
                StateCommandScript.isRUNNUNG -> "isRUNNUNG"
                StateCommandScript.isSTOPING -> "isSTOPING"
                StateCommandScript.isEDITTING -> "isEDITTING"
            }
        return s
    }

    fun log(str: String) {
        consoleLog.println(str)
    }

    suspend fun run() {

        if (end)
            return
        end = false
        yield = false

        if (System.currentTimeMillis() <= endTime)
            return

        endTime = 0

        while (!yield && !end) {
            CMD_EXE()
            if (System.currentTimeMillis() <= endTime)
                return

            delay(10)

        }
    }

    fun openScript(path: String) {

    }

    private fun start() {
        pc.value = 1
        end = false
    }

    private fun stop() {
        pc.value = 1
        end = true
        println("Script Stop")
    }

    private fun pause() {
        end = true
    }

    private fun resume() {
        end = false
    }

    //Выполнить команду по строке pc
    private fun CMD_EXE() {

        val comand: String = list[pc.value]

        println("Script: ${pc.value} $comand")

        log("Script: ${pc.value} $comand")

        //Разобрать строку на список команд
        val listCMD = comand.split(" ")
        if (listCMD.isEmpty()) {
            println("Script: Error размер listCMD == 0")
            return
        }

        when (listCMD[0]) {

            "ELSE" -> {
                //Ищем первое ENDIF
                var currentPC = pc.value
                while (true) {
                    if (list[currentPC] == "ENDIF") {
                        pc.value = currentPC
                        break
                    }
                    currentPC++
                    if (currentPC > (PC_MAX - 1))
                        break
                }
            }

            "ENDIF" -> {
                pc.value++
            }

            "END" -> {
                println("Скрипт окончен")
                end = true
            }

            "IF" -> ifComand()

            "CH1", "CH2", "CR1", "CR2", "AM1", "AM2", "FM1", "FM2" -> {
                generatorComand()
                pc.value++
            }

            "MINUS", "PLUS" -> {
                comandPlusMinus()
                pc.value++
            }

            //"PRINTF" -> { printF() pc++}

            "GOTO" -> {
                pc.value = listCMD[1].toInt()
            }

            "YIELD" -> {
                yield = true
                pc.value++
            }

            "DELAY" -> {
                val d = listCMD[1].toLong()
                endTime = System.currentTimeMillis() + d
                pc.value++
            }

            "TEXT" -> {
                pc.value++
            }

            "LOAD" -> {
                //LOAD F1 2344.0  │ 2344.0 -> F1
                Load()
                pc.value++
            }


            else -> {
                println("Script:? pc:$pc.value:$comand")
                pc.value++
                if (pc.value >= PC_MAX)
                    end = true
            }

        }


    }

    fun Load() {
        //LOAD F1 2344.0  │ 2344.0 -> F1
        val comand: String = list[pc.value]

        //Разобрать строку на список команд
        val listCMD = comand.split(" ")
        if (listCMD.isEmpty()) {
            println("Script: Error Load размер listCMD == 0")
            return
        }

        val index = listCMD[1].drop(1).toInt()
        f[index] = listCMD[2].toFloat()

    }

    // IF R1 < 5500
    private fun ifComand() {

        val comand: String = list[pc.value]

        //Разобрать строку на список команд
        val listCMD = comand.split(" ")
        if (listCMD.isEmpty()) {
            println("Script: Error ifComand размер listCMD == 0")
            return
        }

        //IF Rxx Первый всегда R регистр
        val f1value = f[listCMD[1].drop(1).toInt()]

        val f2value = if ((listCMD[3].first() == 'F'))
            f[listCMD[3].drop(1).toInt()]
        else
            listCMD[3].toFloat()

        // имеем f1value и f2value
        var boolResult = false //Результат сравнения true or false чтобы решить куда дальше

        if ((listCMD[2] == "<") && (f1value < f2value)) boolResult = true
        if ((listCMD[2] == ">") && (f1value > f2value)) boolResult = true
        if ((listCMD[2] == "<=") && (f1value <= f2value)) boolResult = true
        if ((listCMD[2] == ">=") && (f1value >= f2value)) boolResult = true
        if ((listCMD[2] == "!=") && (f1value != f2value)) boolResult = true
        if ((listCMD[2] == "==") && (f1value == f2value)) boolResult = true

        if (boolResult) {
            pc.value++ //Переход на следующую строку, ибо условие выполнено
        } else {
            //Ищем первое ELSE или ENDIF, ибо условие не выполнено
            var currentPC = pc.value
            while (true) {
                if (list[currentPC] == "ELSE") //+1 к ELSE
                {
                    pc.value = currentPC + 1
                    break
                }

                if (list[currentPC] == "ENDIF") {
                    pc.value = currentPC
                    break
                }
                currentPC++
                if (currentPC > (PC_MAX - 1))
                    break
            }
        }
    }

    fun generatorComand() {

        val comand: String = list[pc.value]

        //Разобрать строку на список команд
        val listCMD = comand.split(" ")
        if (listCMD.isEmpty()) {
            println("Script: Error generatorComand размер listCMD == 0")
            return
        }

        var value = 0.0f

        val chanel = listCMD[0].drop(2).toInt() //Номер канала

        //╭─ CH1 CH2 ─────────────────────────────────────────────────────────────────╮
        if ((listCMD[0] == "CH1") || (listCMD[0] == "CH2")) {                       //│                                                                       //│
            val onoff = listCMD[2] == "ON"                                          //│
            //───────────────────────────────────────────┬────────────────────────────┤
            if (listCMD[1] == "CR")                    //│  CH1 CR ON   CH2 CR OFF  //│
            {                                          //╰────────────────────────────┤
                if (chanel == 1)                                                    //│
                    Global.ch1_EN.value = onoff                                     //│
                else                                                                //│
                    Global.ch2_EN.value = onoff                                     //│
            }                                                                       //│
            //────────────────────────────────────────────┬───────────────────────────┤
            if (listCMD[1] == "AM")                     //│  CH1 AM ON   CH2 AM OFF //│
            {                                           //╰───────────────────────────┤
                if (chanel == 1)                                                    //│
                    Global.ch1_AM_EN.value = onoff                                  //│
                else                                                                //│
                    Global.ch2_AM_EN.value = onoff                                  //│
            }                                                                       //│
            //────────────────────────────────────────────────────────────────────────┤
            if (listCMD[1] == "FM")                                                 //│
            {                                                                       //│
                if (chanel == 1)                                                    //│
                    Global.ch1_FM_EN.value = onoff                                  //│
                else                                                                //│
                    Global.ch2_FM_EN.value = onoff                                  //│
            }                                                                       //│
            //────────────────────────────────────────────────────────────────────────┤
            return                                                                  //│
        }                                                                           //│
        //╰───────────────────────────────────────────────────────────────────────────╯

        //╭─ CR1 CR2 ─────────────────────────────────────────────────────────────────╮
        if ((listCMD[0] == "CR1") || (listCMD[0] == "CR2"))                         //│
        {                                                                           //│
            //CR[1 2] MOD 01_Sine_12b                                               //│
            if (listCMD[1] == "MOD")                                                //│
            {                                                                       //│
                println(listCMD[2])                                                 //│
                if (chanel == 1)
                    Global.ch1_Carrier_Filename.value = listCMD[2]                  //│
                else                                                                //│
                    Global.ch2_Carrier_Filename.value = listCMD[2]                  //│
            }                                                                       //│

            //CR[1 2] FR 1000.3                                                     //│
            if (listCMD[1] == "FR")                                                 //│
            {                                                                       //│
                value = if (listCMD[2].first() == 'F') {                            //│
                    f[listCMD[2].drop(1).toInt()]                         //│
                } else                                                              //│
                    listCMD[2].toFloat()                                            //│

                if (chanel == 1)
                    Global.ch1_Carrier_Fr.value = value                             //│
                else                                                                //│
                    Global.ch2_Carrier_Fr.value = value                             //│
            }                                                                       //│
            return                                                                  //│
        }                                                                           //│
        //╰───────────────────────────────────────────────────────────────────────────╯

        //╭─ AM1 AM2 ─────────────────────────────────────────────────────────────────╮
        if ((listCMD[0] == "AM1") || (listCMD[0] == "AM2"))                         //│
        {                                                                           //│
            //SEGGER_RTT_printf(0, "╭─ AM1 AM2 ─╮\n");                              //│
            //│
            //AM[1 2] FR 1000.3                                                     //│
            if (listCMD[1] == "FR")                                                 //│
            {                                                                       //│
                value = if (listCMD[2].first() == 'F')
                    f[listCMD[2].drop(1).toInt()]                      //│
                else                                                                //│
                    listCMD[2].toFloat()                                            //│

                if (chanel == 1)                                                    //│
                    Global.ch1_AM_Fr.value = value                                  //│
                else                                                                //│
                    Global.ch2_AM_Fr.value = value                                  //│
            }                                                                       //│

            //AM[1 2] MOD 02_HWawe { 1.9ms }                                        //│
            if (listCMD[1] == "MOD")                                                //│
            {                                                                       //│
                if (chanel == 1) {                                                  //│
                    Global.ch1_AM_Filename.value = listCMD[2]                       //│
                }                                                                   //│
                else                                                                //│
                    Global.ch2_AM_Filename.value = listCMD[2]                       //│
            }                                                                       //│
            return                                                                  //│
        }                                                                           //│
        //╰───────────────────────────────────────────────────────────────────────────╯
        //╭─ FM1 FM2 ─────────────────────────────────────────────────────────────────╮
        if ((listCMD[0] == "FM1") || (listCMD[0] == "FM2"))                         //│
        {                                                                           //│
            //SEGGER_RTT_printf(0, "╭─ FM1 FM2 ─╮\n");                              //│

            //FM[1 2] BASE 1234.6                                                   //│
            if (listCMD[1] == "BASE")                                               //│
            {                                                                       //│
                value = if (listCMD[2].first() == 'F') {                            //│
                    f[listCMD[2].drop(1).toInt()]                      //│
                } else                                                              //│
                    listCMD[2].toFloat()                                            //│

                if (chanel == 1)                                                    //│
                    Global.ch1_FM_Base.value = value                                //│
                else                                                                //│
                    Global.ch2_FM_Base.value = value                                //│
            }                                                                       //│
        }                                                                           //│
        //│
        //FM[1 2] DEV  123.8                                                        //│
        if (listCMD[1] == "DEV")                                                    //│
        {
            value = if (listCMD[2].first() == 'F') {
                f[listCMD[2].drop(1).toInt()]
            } else
                listCMD[2].toFloat()

            if (chanel == 1)
                Global.ch1_FM_Dev.value = value
            else                                                                    //│
                Global.ch2_FM_Dev.value = value                                     //│
        }                                                                           //│

        //FM[1 2] MOD 02_HWawe                                                      //│
        if (listCMD[1] == "MOD")                                                    //│
        {                                                                           //│
            if (chanel == 1)
                Global.ch1_FM_Filename.value = listCMD[2]
            else                                                                    //│
                Global.ch2_FM_Filename.value = listCMD[2]                           //│
        }                                                                           //│

        //FM[1 2] FR   3.5                                                          //│
        if (listCMD[1] == "FR")                                                     //│
        {                                                                           //│
            value = if (listCMD[2].first() == 'F') {
                f[listCMD[2].drop(1).toInt()]
            } else
                listCMD[2].toFloat()

            if (chanel == 1)
                Global.ch1_FM_Fr.value = value
            else                                                                     //│
                Global.ch2_FM_Fr.value = value                                       //│
        }                                                                            //│
        return                                                                       //│
    }                                                                                //│
    //╰────────────────────────────────────────────────────────────────────────────────╯

    private fun comandPlusMinus() {
        //MINUS F1 5000.0
        //MINUS F1 F2
        val comand: String = list[pc.value]
        //Разобрать строку на список команд
        val listCMD = comand.split(" ")
        if (listCMD.isEmpty()) {
            println("Script: Error comandPlusMinus размер listCMD == 0")
            return
        }
        val index = listCMD[1].drop(1).toInt() //Индекс первой ячейки 0..9
        //MINUS F1 F2
        if (listCMD[2].first() == 'F') {
            //Втокой операнд это регистор
            val secondIndex = listCMD[2].drop(1).toInt() //Индекс второго регистра
            //┌── MINUS ────────────────────────────────────┐
            if (listCMD[0] == "MINUS") {
                f[index] = f[index] - f[secondIndex]  //MINUS F* F*
            }
            //└────────────────────────────────────────────┘
            //┌── PLUS ────────────────────────────────────┐
            if (listCMD[0] == "PLUS") {
                f[index] = f[index] + f[secondIndex]          //PLUS F* F*
            }
            //└────────────────────────────────────────────┘
        } else {
            //Второй операнд это константа
            //MINUS F1 5000.0
            val fvalue = listCMD[2].toFloat()
            //┌── MINUS ───────────────────────────────────┐
            if (listCMD[0] == "MINUS") {
                f[index] = f[index] - fvalue //MINUS F* F*
            }
            //└────────────────────────────────────────────┘
            //┌── PLUS ────────────────────────────────────┐
            if (listCMD[0] == "PLUS") {
                f[index] = f[index] + fvalue //MINUS F* F*
            }
            //└────────────────────────────────────────────┘
        }
    }

    //Тесты
    fun unit5Load() {
        list.clear()
        list.add("T Script Name")
        list.add("T Unit test")
        list.add("LOAD F1 1000")
        list.add("IF F1 < 10000")
        list.add("DELAY 50")
        list.add("CR1 FR F1")
        list.add("DELAY 50")
        list.add("PLUS F1 100")
        list.add("DELAY 50")
        list.add("GOTO 3")
        list.add("ENDIF")
        list.add("T Выход")
        list.add("END")
    }

    /*
     *╭──────────────────────────────────╮
     *│    Область Compose компонентов   │
     *╰──────────────────────────────────╯
     */
    val consoleLog = Console2()
    val consoleUp = Console2()

    init {
        consoleUp.isCheckedUselineVisible.value = true
        consoleLog.isCheckedUselineVisible.value = true

        consoleUp.println("Up6")
        consoleLog.println("Down1")
    }

    @Composable
    fun LoadScriptToConsoleView() {
        consoleUp.colorlineAndText.clear()
        //consoleUp.println("SCRIPT_NAME")
        for (str in list) {
            consoleUp.println(str)
        }
    }

    @Composable
    fun ConsoleLogDraw(modifier: Modifier = Modifier) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.Red)
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                .recomposeHighlighter()
                .then(modifier)
        )
        {
            Column() {

//                Text(
//                    "Логи",
//                    color = Color.White,
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.Center
//                )

                consoleLog.Draw(
                    Modifier
                        .padding(4.dp)
                )
            }
        }
    }

    @Composable
    fun ConsoleViewDraw(modifier: Modifier = Modifier) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.Green)
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                //.recomposeHighlighter()
                .then(modifier)
        )
        {
            consoleUp.SelectLine = pc
            consoleUp.Draw()
        }

        Box(modifier = Modifier.background(Color.Green), contentAlignment = Alignment.BottomEnd)
        {
            Text("PC ${pc.value}")
        }
    }

    @Composable
    fun RegisterViewDraw(modifier: Modifier = Modifier) {
        Box(
            modifier = Modifier
                .padding(start = 6.dp, end = 6.dp)
                .fillMaxWidth()
                //.background(Color.Red)
                //.border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                //.wrapContentHeight()
                .recomposeHighlighter()
                .then(modifier)
        ) {
            Column(
                Modifier
                    .recomposeHighlighterOneLine()
                    .height(50.dp)
            ) {
                Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    repeat(5) {
                        ComposeBoxForF(it, Modifier.weight(1f))
                    }
                }

                Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    repeat(5) {
                        ComposeBoxForF(it + 5, Modifier.weight(1f))
                    }
                }
            }
        }
    }

    @Composable
    private fun ComposeBoxForF(index: Int, modifier: Modifier = Modifier) {
        Box(
            modifier = Modifier
                .padding(start = 1.dp, end = 1.dp)
                .height(25.dp)
                .fillMaxWidth()
                .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                .then(modifier)
            //, contentAlignment = Alignment.CenterStart

        )
        {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .width(12.dp)
                        .height(25.dp)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                )
                {
                    Text(
                        text = "$index",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = "${f[index]}",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }


////////////////////////////////////////////////////////////

    //Показать клавиатуру и привязать ее к индексу
    @Composable
    fun ScriptTable() {

        Box(modifier = Modifier.fillMaxSize(1f))
        {
            Column() {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f), contentAlignment = Alignment.BottomEnd
                    )
                    {
                        ScriptConsole(list, pc.value).Draw()
                        Text(text = "PC:${pc.value}", color = Color.Red)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(160.dp)
                            .background(Color.LightGray), contentAlignment = Alignment.TopCenter
                    )
                    {
                        Column() {
                            if (state != StateCommandScript.isEDITTING) {
                                TemplateButtonBottomBar(str = "New")
                                TemplateButtonBottomBar(str = "Редактирование", onClick = {
                                    command(StateCommandScript.EDIT)
                                })
                                TemplateButtonBottomBar(str = StateToString())
                            }
                            if (state == StateCommandScript.isEDITTING) {
                                TemplateButtonBottomBar(str = "STOP", onClick = {
                                    command(StateCommandScript.STOP)
                                })
                                TemplateButtonBottomBar(str = "Добавить", onClick = {
                                    list.add("?")
                                })
                                TemplateButtonBottomBar(str = "Удалить", onClick = {
                                    command(StateCommandScript.STOP)
                                })
                                TemplateButtonBottomBar(str = StateToString())
                            }
                        }
                    }
                }

                AnimatedVisibility(visible = state == StateCommandScript.isEDITTING) {
                    ScriptKeyboard(pc.value, list).Core()
                }
            }


        }

    }


}

