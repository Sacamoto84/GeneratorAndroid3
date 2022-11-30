package com.example.generator2.screens.editor

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import com.example.generator2.screens.editor.ui.Four
import com.example.generator2.screens.editor.ui.MotionEvent

//Тип рисования
enum class PaintingState {
    Show,                   //Просмотр
    PaintPoint,             //Рисуем точкой
    PaintLine               //Рисуем линией
}

class EditorMatModel {

    val refsresh = mutableStateOf(0)
    val refsreshButton = mutableStateOf(0)

    //4096
    val editMax = 32 //Соличество строк

    //1024
    val editWight = 32 //Количество столбцов

    var state = PaintingState.Show

    var motionEvent =
        mutableStateOf(MotionEvent.Idle)  // This is our motion event we get from touch motion


    val signal: IntArray = IntArray(editWight + 1) { editMax / 2 }


    var sizeCanvas: Size = Size(1f, 1f)  //Размер канвы

    var lastPosition: Offset

    init {
        lastPosition = Offset(sizeCanvas.width / 2, sizeCanvas.height / 2) //Прошлая кордината
    }

    var currentPosition = mutableStateOf(Offset(sizeCanvas.width / 2, sizeCanvas.height / 2))

    var position: Offset = Offset(
        0f, 0f
    )

    //Текущая позиция 1024x1024 //        set(value) { //            lastPosition = position //            val x = map(value.x.toInt(), 0, sizeCanvas.width.toInt() - 1, 0, 1023) //            val y = map(value.y.toInt(), 0, sizeCanvas.height.toInt() - 1, editMin, editMax) //            println("modelPosition $x $y") //            field = Offset(x.toFloat(), y.toFloat()) //        }

    fun setOnlyPosition(p: Offset) {
        val x = map(p.x, 0f, sizeCanvas.width - 1, 0f, editWight.toFloat())
        val y = map(
            p.y, 0f, sizeCanvas.height - 1, 0f, editMax.toFloat() - 1
        )
        //println("setOnlyPosition $x $y")
        position = Offset(x.toFloat(), y.toFloat())
    }

    fun setPositionAndLast(p: Offset) {
        lastPosition = position
        val x = map(p.x, 0f, sizeCanvas.width - 1, 0f, editWight.toFloat())
        val y = map(
            p.y, 0f, sizeCanvas.height - 1, 0f, editMax.toFloat() - 1f
        )
        //println("setPositionAndLast $x $y")
        position = Offset(x.toFloat(), y.toFloat())
    }

    fun setOnlyLast(p: Offset) {
        val x = map(p.x, 0f, sizeCanvas.width - 1, 0f, editWight.toFloat())
        val y = map(
            p.y, 0f, sizeCanvas.height - 1, 0f, editMax.toFloat() - 1
        )
        //println("setLast $x $y")
        lastPosition = Offset(x.toFloat(), y.toFloat())
    }


    fun line() {

        var x0 = position.x.toInt()
        var y0 = position.y.toInt()

        var x1 = lastPosition.x.toInt()
        var y1 = lastPosition.y.toInt()

        var tmp = 0

        /* Check for overflow */
        if (x0 > editWight) {
            x0 = editWight
        }

        if (x1 > editWight) {
            x1 = editWight
        }

        if (y0 > editMax) {
            y0 = editMax
        }

        if (y1 > editMax) {
            y1 = editMax
        }


        if (x0 < 0) x0 = 0

        if (y0 < 0) y0 = 0

        if (x1 < 0) x1 = 0

        if (y1 < 0) y1 = 0

        val dx = if (x0 < x1) x1 - x0 else x0 - x1
        val dy = if (y0 < y1) y1 - y0 else y0 - y1
        val sx = if (x0 < x1) 1 else -1
        val sy = if (y0 < y1) 1 else -1
        var err = (if (dx > dy) dx else -dy) / 2


        //Вертикальная линия
        if (dx == 0) {
            if (y1 < y0) {
                tmp = y1
                y1 = y0
                y0 = tmp
            }
            if (x1 < x0) {
                tmp = x1
                x1 = x0
                x0 = tmp
            }/* Vertical line */ //for (i = y0; i <= y1; i++) {
            //   SetPixel(x0, i, c)
            //}
            signal[x0] = y0 //map(y0, editMin, editMax, 0, sizeCanvas.width.toInt() - 1 )
            return
        }

        //Горизонтальная линия
        if (dy == 0) {
            if (y1 < y0) {
                tmp = y1
                y1 = y0
                y0 = tmp
            }

            if (x1 < x0) {
                tmp = x1
                x1 = x0
                x0 = tmp
            }

            /* Horizontal line */ //for (i = x0; i <= x1; i++) {
            for (i in x0..x1) {
                signal[i] = y0 // map(y0, editMin, editMax, 0, sizeCanvas.width.toInt() - 1 )
            }

            return
        }

        while (true) {

            signal[x0] = y0 //map(y0, editMin, editMax, 0, sizeCanvas.width.toInt() - 1 )

            //SetPixel(x0, y0, c)

            if (x0 == x1 && y0 == y1) {
                break
            }

            val e2 = err
            if (e2 > -dx) {
                err -= dy
                x0 += sx
            }
            if (e2 < dy) {
                err += dx
                y0 += sy
            }
        }
    }

    /**
     * Создать точки из signal для отображения
     */
    fun createPoint(size: Size = sizeCanvas): MutableList<Offset> {

        val points = mutableListOf<Offset>()

        signal[signal.lastIndex] = signal[signal.lastIndex - 1]

        for (x in 0 until size.width.toInt()) {

            val mapX: Int = map(x.toFloat(), 0f, size.width - 1, 0f, editWight.toFloat()).toInt()

            val y = map(signal[mapX].toFloat(), 0f, editMax.toFloat(), 0f, size.height - 1).toFloat()
            points.add(Offset(x.toFloat(), y))

        }

        return points
    }

    /**
     * Создать точки из signal для отображения
     */
    fun createPointLoop(size: Size = sizeCanvas): Four<MutableList<Offset>, MutableList<Offset>, Path, Path> {

        var gain = 8

        val points = mutableListOf<Offset>()
        val points2 = mutableListOf<Offset>()
        val points3 = mutableListOf<Offset>()
        val points4 = mutableListOf<Offset>()

        gain = 8

        val pathRect = Path() //Вертикальная полоса
        val pathRef = Path()  //Референсные линии

        for (x in 0 until editWight * gain) {
            val y = gain * signal[x / gain]
            points.add(
                Offset(
                    size.width / 2 + x.toFloat() - position.x * gain,
                    size.height / 2 + y.toFloat() - position.y * gain
                )
            )

            //Внешний квадрат
            if (x == 0) {
                val top = size.height / 2 - position.y * gain
                val bottom = size.height / 2 - position.y * gain + editMax * gain
                val left = size.width / 2 - position.x * gain
                val right = size.width / 2 - position.x * gain + editWight * gain

                pathRect.reset()
                pathRect.addRect(
                    Rect(
                        topLeft = Offset(left, top),
                        bottomRight = Offset(right, bottom)
                    )
                )

                //Референсные линии
                pathRef.reset()
                pathRef.moveTo(left, size.height / 2 - position.y * gain + editMax * gain / 2)
                pathRef.lineTo(right, size.height / 2 - position.y * gain + editMax * gain / 2)
                pathRef.moveTo(size.width / 2 - position.x * gain + editWight * gain / 2, top)
                pathRef.lineTo(size.width / 2 - position.x * gain + editWight * gain / 2, bottom)
            }

        }

        val out: Four<MutableList<Offset>, MutableList<Offset>, Path, Path> =
            Four(points, points2, pathRef, pathRect)

        return out
    }


    //position->modelPosition
    fun map(
        x: Float, in_min: Float, in_max: Float, out_min: Float, out_max: Float
    ): Float {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    }


    fun constrain(amt: Int, low: Int, high: Int): Int {

        return if (amt < low) {
            low
        } else {
            if (amt > high) {
                high
            } else {
                amt
            }
        }

    }

}