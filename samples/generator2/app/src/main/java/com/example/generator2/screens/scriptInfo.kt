package com.example.generator2.scripting

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.generator2.Global
import com.example.generator2.console.Console2


//Экран описания языка
@Composable
fun ScreenScriptInfo()
{

    Global.script.start() //Запустить скрипт на выполнение

    val console = Console2()
    Info0(console)

    console.Draw()

}

@Preview
@Composable
fun ScreeScriptInfoReview(){
    ScreenScriptInfo()
}

@Composable
fun Info0( console: Console2 )
{
    console.println("Описание работы скрипов")
    console.println("╭─── Генератор ────────────────────╮")
    console.println("│ CH[1 2] [CR AM FM] [ON OFF]      │")
    console.println("├────────────────────────────      │")
    console.println("│ CR[1 2] FR 1000.0    |           │")
    console.println("│ CR[1 2] FR F[0..9]   | CR1 FR F2 │")
    console.println("│ CR[1 2] MOD 02_HWawe |           │")
    console.println("│────────────────────────────      │")
    console.println("│ AM[1 2] FR 1000.3                │")
    console.println("│ AM[1 2] MOD 02_HWawe             │")
    console.println("│ ───────────────────────────      │")
    console.println("│ FM[1 2] BASE 1234.6   F[]        │")
    console.println("│ FM[1 2] DEV  123.8    F[]        │")
    console.println("│ FM[1 2] MOD  02_HWawe            │")
    console.println("│ FM[1 2] FR   3.5      F[]        │")
    console.println("╰──────────────────────────────────╯")

    console.println("╭─ Выйти ─╮╭─ Переход ─╮╭─ Задержка ─╮")
    console.println("│ YIELD   ││ GOTO 2    ││ DELAY 4000 │")
    console.println("╰─────────╯╰───────────╯╰────────────╯")
    console.println("╭─ Завершение ─╮╭─ Текст ────╮")
    console.println("│ END          ││ TEXT Текст │")
    console.println("╰──────────────╯╰────────────╯")

    console.println("╭─ Арифметика ────┬─────────────────────╮")
    console.println("│ MINUS F1 5000.0 │ F1 - 5.1 -> F1      │")
    console.println("│ MINUS F1 F2     │ F1 - F2 -> F1       │")
    console.println("│ PLUS F1 4555.5  │ F1 + 4555.5 -> 4555 │")
    console.println("│ PLUS F1 F2      │ F1 + F2 -> F1       │")
    console.println("╰─────────────────┴─────────────────────╯")

    console.println("╭────────────┬──────────────╮")
    console.println("│IF F1 < 450 │ IF F1 < 450  │")
    console.println("│...{true}   │ ...  {true}  │")
    console.println("│ELSE        │ ENDIF        │")
    console.println("│...{false}  │ ...  {false} │")
    console.println("│ENDIF       │              │")
    console.println("├───┬────┬───┼────┬────┬────┤")
    console.println("│ < │ <= │ > │ >= │ == │ != │")
    console.println("╰───┴────┴───┴────┴────┴────╯")
    
    console.println("╭─ Загрузка константы в регистр ─╮")
    console.println("│ LOAD F1 2344.0  │ 2344.0 -> F1 │")
    console.println("╰─────────────────┴──────────────╯")

}
