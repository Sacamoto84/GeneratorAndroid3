package com.example.generator2.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlin.math.sin


class EditorMatModel {

    val points: MutableList<Offset> = mutableListOf()


    private val tableSignal: Array<Array<Int>> = Array(1024) { Array(1024) { 0 } }

    private val tableMouse: Array<Array<Int>> = Array(1024) { Array(1024) { 0 } }

    private val signal: IntArray =  IntArray(1024) { 512 }

    fun editorMatModel() {

    }

    private fun setPixel(x: Int, y: Int, color: Int, table: Array<Array<Int>>) {
        if ((x < 0) || (y < 0) || (x > 1023) || (y > 1023)) return

        table[x][y] = color
    }

    fun getPixel(x: Int, y: Int, table: Array<Array<Int>>): Int {
        if ((x < 0) || (y < 0) || (x > 1023) || (y > 1023)) return -1

        return table[x][y]
    }


    private fun lineH(Y: Int, X1: Int, X2: Int, color: Int, table: Array<Array<Int>>) {

        var x1 = X1
        var x2 = X2

        if (X2 >= 1024) x2 = 1023

        if (X2 < 0) x2 = 0

        if (X1 >= 1024) x1 = 1023

        if (X1 < 0) x1 = 0

        if (Y >= 1024) return

        if (X1 >= 1024) return

        for (i in X1..x2) {
            setPixel(i, Y, color, table)
        }
    }

    private fun rectangle(x: Int, y: Int, W: Int, H: Int, c: Int, table: Array<Array<Int>>) {

        if (x >= 1024 || y >= 1024) return;

        var w = W

        var h = H

        /* Check width and height */
        if ((x + w) >= 1024) {
            w = 1024 - x;
        }
        if ((y + h) >= 1024) {
            h = 1024 - y;
        }

        /* Draw 4 lines */
        lineH(y, x, x + w, c ,table);
        lineH(y + h, x, x + w, c ,table);

        lineV(x, y, y + h, c ,table);
        lineV(x + w, y, y + h, c ,table);
    }

    private fun lineV(X: Int, Y1: Int, Y2: Int, color: Int, table: Array<Array<Int>>) {

        var y1 = Y1
        var y2 = Y2

        if (y1 < 0) y1 = 0

        if (y2 >= 1024) y2 = 1023
        if (y2 < 0) return

        if (y1 > y2) return

        if ((X < 0) || (X >= 1024)) return

        for (i in y1..y2) //uTFT_SetPixel(X, i, color);
        {
            setPixel(X, i, color ,table)
        }

    }


    /**
     * Создать точки из signal
     */
    fun createPoint(size: Size) : MutableList<Offset>
    {

        signal[64] = 0
        signal[128] = 800

        val points = mutableListOf<Offset>()
        val verticalCenter = size.height / 2

        val sizeW = size.width.toInt()

        for (x in 0 until size.width.toInt() step 1) {

            val mapX : Int = map(x , 0, sizeW - 1, 0, 1023)

            val y = signal[mapX].toFloat()
            points.add(Offset(x.toFloat(), y))



        }
        return points
    }

    private fun map(x: Int, in_min: Int, in_max: Int, out_min: Int, out_max: Int): Int {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    }


}
