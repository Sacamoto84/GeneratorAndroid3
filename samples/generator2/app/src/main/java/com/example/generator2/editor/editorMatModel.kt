package com.example.generator2.editor

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import kotlin.math.sin

//Тип рисования
enum class PaintingState {
    Show,                   //Просмотр
    PaintPoint,             //Рисуем точкой
    PaintLine               //Рисуем линией
}

class EditorMatModel {

    var state = PaintingState.Show

    private val signal: IntArray = IntArray(1024) { 2047 }

    var sizeCanvas: Size = Size(1f, 1f)  //Размер канвы

    var lastPosition: Offset

    init {
        lastPosition = Offset(0f, 0f) //Прошлая кордината
    }

    var currentPosition = mutableStateOf(Offset.Unspecified)

    var position: Offset = Offset(
        0f, 0f
    )      //Текущая позиция 1024x1024 //        set(value) { //            lastPosition = position //            val x = map(value.x.toInt(), 0, sizeCanvas.width.toInt() - 1, 0, 1023)
    //            val y = map(value.y.toInt(), 0, sizeCanvas.height.toInt() - 1, editMin, editMax)
    //            println("modelPosition $x $y")
    //            field = Offset(x.toFloat(), y.toFloat())
    //        }

    fun setOnlyPosition(p: Offset) {
        val x = map(p.x.toInt(), 0, sizeCanvas.width.toInt() - 1, 0, 1023)
        val y = map(p.y.toInt(), 0, sizeCanvas.height.toInt() - 1, editMin, editMax)
        println("setOnlyPosition $x $y")
        position = Offset(x.toFloat(), y.toFloat())
    }

    fun setPositionAndLast(p: Offset) {
        lastPosition = position
        val x = map(p.x.toInt(), 0, sizeCanvas.width.toInt() - 1, 0, 1023)
        val y = map(p.y.toInt(), 0, sizeCanvas.height.toInt() - 1, editMin, editMax)
        println("setPositionAndLast $x $y")
        position = Offset(x.toFloat(), y.toFloat())
    }

    fun setOnlyLast(p: Offset) {
        val x = map(p.x.toInt(), 0, sizeCanvas.width.toInt() - 1, 0, 1023)
        val y = map(p.y.toInt(), 0, sizeCanvas.height.toInt() - 1, editMin, editMax)
        println("setLast $x $y")
        lastPosition = Offset(x.toFloat(), y.toFloat())
    }


    fun line() {

        var x0 = position.x.toInt()
        var y0 = position.y.toInt()

        var x1 = lastPosition.x.toInt()
        var y1 = lastPosition.y.toInt()

        var tmp = 0

        /* Check for overflow */
        if (x0 >= 1024) {
            x0 = 1023
        }
        if (x1 >= 1024) {
            x1 = 1023
        }
        if (y0 >= editMax + 1) {
            y0 = editMax
        }
        if (y1 >= editMax + 1) {
            y1 = editMax
        }

        if (x0 < 0) x0 = 0

        if (y0 < editMin) y0 = editMin

        if (x1 < 0) x1 = 0

        if (y1 < editMin) y1 = editMin

        val dx = if (x0 < x1) (x1 - x0) else (x0 - x1)
        val dy = if (y0 < y1) (y1 - y0) else (y0 - y1)
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
            //    SetPixel(i, y0, c)
            //}

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
        val verticalCenter = size.height / 2

        val sizeW = size.width.toInt()

        for (x in 0 until size.width.toInt() step 1) {
            val mapX: Int = map(x, 0, sizeW - 1, 0, editWight - 1)
            val y = map(signal[mapX], editMin, editMax, 0, size.height.toInt() - 1).toFloat()
            points.add(Offset(x.toFloat(), y))
        }
        return points
    }

    /**
     * Создать точки из signal для отображения
     */
    fun createPointLoop(size: Size = sizeCanvas): Pair<MutableList<Offset>, MutableList<Offset>> {

        val points = mutableListOf<Offset>()
        val points2 = mutableListOf<Offset>()

        val verticalCenter = size.height / 2

        val sizeW = size.width.toInt()


        var start = (position.x - 50f).toInt()
        var stop = (position.x + 50f).toInt()

        if (start < 0) start = 0


        stop = start + 100

        if (stop > 1023) {
            stop = 1023
            start = stop - 100
        }

        var startOffset = size.width / 2 - 50f

        start *= 4
        stop *= 4

        for (x in start..stop) {
            val y = (map(
                signal[x / 4],
                editMin,
                editMax,
                0,
                size.height.toInt() - 1
            ).toFloat()) * 2f - 350f


            points2.add(
                Offset(
                    startOffset,
                    (map(
                        position.y.toInt(),
                        editMin,
                        editMax,
                        0,
                        size.height.toInt() - 1
                    ).toFloat()) * 2f - 350f
                )
            )


            points.add(Offset(startOffset++, y))


        }

        var out: Pair<MutableList<Offset>, MutableList<Offset>> =
            Pair(points, points2) //out.first = points
        return out
    }

    //position->modelPosition
    private fun map(
        x: Int,
        in_min: Int = 0,
        in_max: Int = sizeCanvas.width.toInt() - 1,
        out_min: Int = 0,
        out_max: Int = 1023
    ): Int {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    }


    companion object {
        private const val sizeMouse = 10

        private const val editWight = 1024
        private const val editHeight = 1024

        private const val editMax = 4095
        private const val editMin = 0

    }

}
