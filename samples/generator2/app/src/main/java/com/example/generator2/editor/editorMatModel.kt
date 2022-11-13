package com.example.generator2.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import kotlin.math.sin


class EditorMatModel {

    val points: MutableList<Offset> = mutableListOf()

    private val tableSignal: Array<Array<Int>> = Array(1024) { Array(1024) { 0 } }
    private val tableMouse: Array<Array<Int>> = Array(1024) { Array(1024) { 0 } }

    private val signal: IntArray = IntArray(1024) { 512 }

    var sizeCanvas: Size = Size(1f, 1f)  //Размер канвы

    var lastPosition : Offset

    init {
        lastPosition = Offset(0f, 0f) //Прошлая кордината
    }

    var position: Offset = Offset(0f, 0f)      //Текущая позиция 1024x1024
        set(value) {
            lastPosition = position
            val x = map(value.x.toInt(), 0, sizeCanvas.width.toInt() - 1, 0, 1023)
            val y = map(value.y.toInt(), 0, sizeCanvas.width.toInt() - 1, 0, 1023)
            println("modelPosition $x $y")
            field = Offset(x.toFloat(), y.toFloat())
        }

    companion object {
        private const val sizeMouse = 10
    }

    fun calculateBox() {
        var pStart = (position.x - map(sizeMouse)).toInt()
        var pEnd = (position.x + map(sizeMouse)).toInt()

        if (pStart < 0) {
            pEnd = pStart + 2 * sizeMouse
            pStart = 0

            if (pEnd < 0) pEnd = 0

        }

        if (pStart > 1023 - 2 * sizeMouse) {
            pEnd = 1023
        }

        if (pStart > 1023) {
            pStart = 1023
            pEnd = 1023
        }

        line()

//        for (x in pStart..pEnd) {
//
//            //if ((signal[x] > position.y.toInt()) && (signal[x] < (position.y.toInt() + 50f)))
//
//            signal[x] = amap(position.y.toInt())
//            if (signal[x] >= 512 + 256) signal[x] = 512 + 256
//            if (signal[x] < 256) signal[x] = 256
//
//
//            //if ((position.y > signal[x]) && (signal[x] > ( position.y - 50f))) signal[x] = (position.y - 40).toInt()
//        }
//
//        //signal[position.x.toInt()]  = amap (position.y.toInt())

    }

    private fun line() {

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
        if (y0 >= 1024) {
            y0 = 1023
        }
        if (y1 >= 1024) {
            y1 = 1023
        }

        if (x0 < 0)
            x0 = 0

        if (y0 < 0)
            y0 = 0

        if (x1 < 0)
            x1 = 0

        if (y1 < 0)
            y1 = 0

        val dx = if(x0 < x1)  (x1 - x0) else (x0 - x1)
        val dy = if(y0 < y1)  (y1 - y0) else (y0 - y1)
        val sx = if(x0 < x1)  1 else -1
        val sy = if(y0 < y1)  1 else -1
        var err = (if(dx > dy) dx else -dy) / 2


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
            }
            /* Vertical line */
            //for (i = y0; i <= y1; i++) {
             //   SetPixel(x0, i, c)
            //}
            signal[x0] =  amap (y0)
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

            /* Horizontal line */
            //for (i = x0; i <= x1; i++) {
            //    SetPixel(i, y0, c)
            //}

            for(i in x0..x1) {
                signal[i] =  amap(y0)
            }

            return
        }

        while (true) {

            signal[x0] =  amap(y0)

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
     * Создать точки из signal
     */
    fun createPoint(size: Size): MutableList<Offset> {

        val points = mutableListOf<Offset>()
        val verticalCenter = size.height / 2

        val sizeW = size.width.toInt()

        for (x in 0 until size.width.toInt() step 1) {
            val mapX: Int = map(x, 0, sizeW - 1, 0, 1023)
            val y = signal[mapX].toFloat()
            points.add(Offset(x.toFloat(), y))
        }
        return points
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

    //modelPosition->position
    private fun amap(
        x: Int,
        in_min: Int = 0,
        in_max: Int = 1023,
        out_min: Int = 0,
        out_max: Int = sizeCanvas.width.toInt() - 1
    ): Int {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    }










}
