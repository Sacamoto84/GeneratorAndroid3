package com.example.generator2.scripting

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
const val CMD_LEN = 24 //Строки


//Основной класс скриптовой системы
class Script {

    //╭─ Генератор ───────────────────────╮
    var scriptName: String = ""    //Имя текущего скрипта

    //pair_operand   pair;
    //triple_operand triple;

    var end = true
    var yield = false
    var pc = 0

    var endTime = 0              //Время > которого можно продолжать работу

    var F = FloatArray(10)  //Массив регистров

    var str: String = ""

    //Временная строка
    val list: MutableList<String> = mutableListOf<String>() //Список команд
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

            //"IF" -> ifComand()
            //"CH1", "CH2", "CR1", "CR2", "AM1", "AM2","FM1","FM2" -> {generatorComand() pc++}
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


}