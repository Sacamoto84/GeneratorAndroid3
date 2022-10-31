package com.example.generator2.scripting

import com.example.generator2.Global
import com.example.generator2.screens.consoleLog

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

    var endTime = 0L              //Время > которого можно продолжать работу

    var F = FloatArray(10)  //Массив регистров

    var str: String = ""

    //Временная строка
    private val list: MutableList<String> = mutableListOf<String>() //Список команд

//╰─────────────────────────────╯

    fun log( str : String)
    {
        consoleLog.println(str)
    }


    fun run() {

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
        }
    }
    
    
    
    fun openScript(path: String) {

    }

    fun start() {
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
    private fun CMD_EXE() {

        val comand: String = list[pc]

        println("Script: $pc $comand")

        log("Script: $pc $comand")

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

            "MINUS", "PLUS" -> {
                comandPlusMinus()
                pc++
            }

            //"PRINTF" -> { printF() pc++}

            "GOTO" -> {
                pc = listCMD[1].toInt()
            }

            "YIELD" -> {
                yield = true
                pc++
            }

            "DELAY" -> {
                val d = listCMD[1].toLong()
                endTime = System.currentTimeMillis() + d
                pc++
            }

            "TEXT" -> pc++

            "LOAD" -> {
                //LOAD F1 2344.0  │ 2344.0 -> F1
                Load()
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
    
    fun printF(): String {
        val s =
            "0:%F[0] 1:%F[0] 2:%F[0] 3:%F[0]4:%F[0] 5:%F[0] 6:%F[0] 7:%F[0] 8:%F[0] 9:%F[0]"
        return s
    }


    fun Load() {
        //LOAD F1 2344.0  │ 2344.0 -> F1
        val comand: String = list[pc]

        //Разобрать строку на список команд
        val listCMD = comand.split(" ")
        if (listCMD.isEmpty()) {
            println("Script: Error Load размер listCMD == 0")
            return
        }

        val index = listCMD[1].drop(1).toInt()
        F[index] = listCMD[2].toFloat()

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
        val f1value = F[listCMD[1].drop(1).toInt()]

        var f2value = if ((listCMD[3].first() == 'R'))
            F[listCMD[3].drop(1).toInt()]
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
                    F[listCMD[2].drop(1).toInt()]                         //│
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
                value = if (listCMD[2].first() == 'F') {                            //│
                    F[listCMD[2].drop(1).toInt()]                         //│
                } else                                                              //│
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
                    F[listCMD[2].drop(1).toInt()]                         //│
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
                F[listCMD[2].drop(1).toInt()]
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
                F[listCMD[2].drop(1).toInt()]
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

    fun comandPlusMinus() {
        //MINUS F1 5000.0
        //MINUS F1 F2
        val comand: String = list[pc]
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
                F[index] = F[index] - F[secondIndex]  //MINUS F* F*
            }
            //└────────────────────────────────────────────┘
            //┌── PLUS ────────────────────────────────────┐
            if (listCMD[0] == "PLUS") {
                F[index] = F[index] + F[secondIndex]          //PLUS F* F*
            }
            //└────────────────────────────────────────────┘
        } else {
            //Второй операнд это константа
            //MINUS F1 5000.0
            val fvalue = listCMD[2].toFloat()
            //┌── MINUS ───────────────────────────────────┐
            if (listCMD[0] == "MINUS") {
                F[index] = F[index] - fvalue //MINUS F* F*
            }
            //└────────────────────────────────────────────┘
            //┌── PLUS ────────────────────────────────────┐
            if (listCMD[0] == "PLUS") {
                F[index] = F[index] + fvalue //MINUS F* F*
            }
            //└────────────────────────────────────────────┘
        }
    }

    //Тесты
    fun unit5Load()
    {
        list.clear()
        list.add("Unit test")
        list.add("T Юнит тест")
        list.add("LOAD F1 1000")
        list.add("IF F1 < 10000")
        list.add("CR1 FR F1")
        list.add("PLUS F1 100")
        list.add("DELAY 1000")
        list.add("GOTO 3")
        list.add("ENDIF")
        list.add("T Выход")
        list.add("END")
    }

}