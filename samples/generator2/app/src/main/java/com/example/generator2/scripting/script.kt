package com.example.generator2.scripting

import androidx.compose.ui.input.key.Key.Companion.F
import com.example.generator2.Global
import com.example.generator2.Utils

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

//Основной класс скриптовой системы
class Script {

    //╭─ Генератор ───────────────────────╮
    var scriptName: String = ""    //Имя текущего скрипта

    var end = true
    var yield = false
    var pc = 0

    var endTime = 0              //Время > которого можно продолжать работу

    var register = FloatArray(10)  //Массив регистров

    var str: String = ""

    //Временная строка
    private val list: MutableList<String> = mutableListOf<String>() //Список команд
    var line = 0;                                           //Текучая строка
//╰─────────────────────────────╯


    fun openScript(path: String) {

    }

    fun run() {
        pc = 1
        end = false
    }

    fun stop() {
        pc = 1
        end = true
        println("Script Stop")
    }

    fun pause() {
        end = true
    }

    fun resume() {
        end = false
    }

    //Выполнить команду по строке pc
    fun CMD_EXE() {

        val comand: String = list[pc]

        println("Script: $pc $comand")

        //Разобрать строку на список команд
        val listCMD = comand.split(" ")
        if (listCMD.isEmpty()) {
            println("Script: Error размер listCMD == 0")
            return
        }

        when (listCMD[0]) {

            "ELSE" -> {
                //Ищем первое ENDIF
                var currentPC = pc
                while (true) {
                    if (list[currentPC] == "ENDIF") {
                        pc = currentPC
                        break
                    }
                    currentPC++
                    if (currentPC > (PC_MAX - 1))
                        break
                }
            }

            "ENDIF" -> pc++

            "END" -> {
                println("Скрипт окончен")
                end = true
            }

            "IF" -> ifComand()

            "CH1", "CH2", "CR1", "CR2", "AM1", "AM2", "FM1", "FM2" -> {
                generatorComand()
                pc++
            }

            //"MINUS", "PLUS" -> {comandPlusMinus() pc++}
            //"PRINTF" -> { printF() pc++}
            //"GOTO" -> {  pc = comand.toUint(4); } ???????
            "YIELD" -> {
                yield = true
                pc++
            }
            //"DELAY" -> {uint32_t d = comand . toUint (5);
            //    endTime = uwTick + d;
            //    pc++;}

            "TEXT" -> pc++

            "LOAD" -> {
                //triple = excretionTripleOperand(comand);
                //if (triple.operand0 == (char*)"LOAD")
                // {
                //    int index = triple.operand1.buf[1]-0x30;
                //    F[index] = triple.operand2.toFloat();
                //}
                pc++
            }

            else -> {
                println("Script:? pc:$pc:$comand")
                pc++
                if (pc >= PC_MAX)
                    end = true
            }

        }

    }


    // IF R1 < 5500
    fun ifComand() {

        val comand: String = list[pc]

        //Разобрать строку на список команд
        val listCMD = comand.split(" ")
        if (listCMD.isEmpty()) {
            println("Script: Error ifComand размер listCMD == 0")
            return
        }

        //IF Rxx Первый всегда R регистр
        val f1value = register[listCMD[1].drop(1).toInt()]

        var f2value = if ((listCMD[3].first() == 'R'))
            register[listCMD[3].drop(1).toInt()]
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
            pc++ //Переход на следующую строку, ибо условие выполнено
        } else {
            //Ищем первое ELSE или ENDIF, ибо условие не выполнено
            var currentPC = pc
            while (true) {
                if (list[currentPC] == "ELSE") //+1 к ELSE
                {
                    pc = currentPC + 1
                    break
                }

                if (list[currentPC] == "ENDIF") {
                    pc = currentPC
                    break
                }
                currentPC++
                if (currentPC > (PC_MAX - 1))
                    break
            }
        }
    }

    fun generatorComand() {

        val comand: String = list[pc]

        //Разобрать строку на список команд
        val listCMD = comand.split(" ")
        if (listCMD.isEmpty()) {
            println("Script: Error generatorComand размер listCMD == 0")
            return
        }

        var value = 0.0f

        val chanel = listCMD[0].drop(2).toInt() //Номер канала

        //var onoff = false

        //╭─ CH1 CH2 ─────────────────────────────────────────────────────────────────╮
        if ((listCMD[0] == "CH1") || (listCMD[0] == "CH2")) {                       //│                                                                       //│
            val onoff = listCMD[2] == "ON"                                          //│
            //│
            if (listCMD[1] == "CR")                                                 //│
            {                                                                       //│
                if (chanel == 1)                                                    //│
                    Global.ch1_EN.value = onoff                                     //│
                else                                                                //│
                    Global.ch2_EN.value = onoff                                     //│
            }                                                                       //│
            //│
            if (listCMD[1] == "AM")                                                 //│
            {                                                                       //│
                if (chanel == 1)                                                    //│
                    Global.ch1_AM_EN.value = onoff                                  //│
                else                                                                //│
                    Global.ch2_AM_EN.value = onoff                                  //│
            }                                                                       //│
            //│
            if (listCMD[1] == "FM")                                                 //│
            {                                                                       //│
                if (chanel == 1)                                                    //│
                    Global.ch1_FM_EN.value = onoff                                  //│
                else                                                                //│
                    Global.ch2_FM_EN.value = onoff                                  //│
            }                                                                       //│
            //│
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
                //│
                if (chanel == 1) {                                                  //│
                    Global.ch1_Carrier_Filename.value = listCMD[2]                  //│
                    Utils.Spinner_Send_Buffer("CH0", "CR", listCMD[2])     //│
                }                                                                   //│
                else                                                                //│
                {                                                                   //│
                    Global.ch2_Carrier_Filename.value = listCMD[2]                  //│
                    Utils.Spinner_Send_Buffer("CH1", "CR", listCMD[2])    //│
                }                                                                   //│
                //│
            }                                                                       //│
            //│
            //CR[1 2] FR 1000.3                                                     //│
            if (listCMD[1] == "FR")                                                 //│
            {                                                                       //│
                value = if (listCMD[2].first() == 'F') {      //│
                    register[listCMD[2].drop(1).toInt()]                         //│
                } else  //│
                    listCMD[2].toFloat()  //│

                if (chanel == 1)
                    Global.ch1_Carrier_Fr.value = value                               //│
                else                                                                  //│
                    Global.ch2_Carrier_Fr.value = value                               //│
                //│
            }                                                                         //│
            //│
            return                                                                    //│
        }                                                                             //│
        //╰─────────────────────────────────────────────────────────────────────────────╯

        //╭─ AM1 AM2 ───────────────────────────────────────────────────────────────────╮
        if ((listCMD[0] == "AM1") || (listCMD[0] == "AM2"))                           //│
        {                                                                             //│
            //SEGGER_RTT_printf(0, "╭─ AM1 AM2 ─╮\n");                                //│
            //│
            //AM[1 2] FR 1000.3                                                       //│
            if (listCMD[1] == "FR")                                                   //│
            {                                                                         //│
                value = if (listCMD[2].first() == 'F') {                              //│
                    register[listCMD[2].drop(1).toInt()]                           //│
                } else                                                                //│
                    listCMD[2].toFloat()                                              //│
                //│
                if (chanel == 1)                                                      //│
                    Global.ch1_AM_Fr.value = value                                    //│
                else                                                                  //│
                    Global.ch2_AM_Fr.value = value                                    //│
            }                                                                         //│
            //│
            //AM[1 2] MOD 02_HWawe { 1.9ms }                                          //│
            if (listCMD[1] == "MOD")                                                  //│
            {                                                                         //│
                if (chanel == 1) {                                                    //│
                    Global.ch1_AM_Filename.value = listCMD[2]                         //│
                    Utils.Spinner_Send_Buffer("CH0", "AM", listCMD[2])       //│
                }                                                                     //│
                else                                                                  //│
                {                                                                     //│
                    Global.ch2_AM_Filename.value = listCMD[2]                         //│
                    Utils.Spinner_Send_Buffer("CH1", "AM", listCMD[2])       //│
                }                                                                     //│
            }                                                                         //│
            return                                                                    //│
        }                                                                             //│
        //╰─────────────────────────────────────────────────────────────────────────────╯
        //╭─ FM1 FM2 ───────────────────────────────────────────────────────────────────╮
        if ((listCMD[0] == "FM1") || (listCMD[0] == "FM2"))                           //│
        {                                                                             //│
            //SEGGER_RTT_printf(0, "╭─ FM1 FM2 ─╮\n");                                //│
            //│
            //FM[1 2] BASE 1234.6                                                     //│
            if (listCMD[1] == "BASE")                                                 //│
            {                                                                         //│
                value = if (listCMD[2].first() == 'F') {                              //│
                    register[listCMD[2].drop(1).toInt()]                           //│
                } else                                                                //│
                    listCMD[2].toFloat()                                              //│

                if (chanel == 1)                                                      //│
                    Global.ch1_FM_Base.value = value                                  //│
                else                                                                  //│
                    Global.ch2_FM_Base.value = value                                  //│
            }                                                                         //│
        }                                                                             //│
        //│
        //FM[1 2] DEV  123.8                                                          //│
        if (listCMD[1] == "DEV")                                                      //│
        {
            value = if (listCMD[2].first() == 'F') {
                register[listCMD[2].drop(1).toInt()]
            } else
                listCMD[2].toFloat()

            if (chanel == 1)
                Global.ch1_FM_Dev.value = value
            //│
            else                                                                     //│
                Global.ch2_FM_Dev.value = value                                      //│


        }                                                                       //│
        //│
        //FM[1 2] MOD 02_HWawe                                                  //│
        if (listCMD[1] == "MOD")                                                //│
        {                                                                       //│
            if (chanel == 1) {
                Global.ch1_FM_Filename.value = listCMD[2]
                Utils.Spinner_Send_Buffer("CH0", "FM", listCMD[2])
            }                                                                   //│
            else                                                                //│
            {                                                                   //│
                Global.ch2_FM_Filename.value = listCMD[2]
                Utils.Spinner_Send_Buffer("CH1", "FM", listCMD[2])    //│
            }                                                                   //│
        }                                                                       //│

        //FM[1 2] FR   3.5                                                      //│
        if (listCMD[1] == "FR")                                                 //│
        {                                                                       //│
            value = if (listCMD[2].first() == 'F') {
                register[listCMD[2].drop(1).toInt()]
            } else
                listCMD[2].toFloat()

            if (chanel == 1)
                Global.ch1_FM_Fr.value = value
            else                                                                 //│
                Global.ch2_FM_Fr.value = value                                   //│
        }                                                                        //│
        return                                                                   //│
    }                                                                            //│
    //╰────────────────────────────────────────────────────────────────────────────╯
}


}