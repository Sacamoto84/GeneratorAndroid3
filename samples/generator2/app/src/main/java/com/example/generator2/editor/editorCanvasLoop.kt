import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.gesture.pointerMotionEvents

@Composable
fun EditorCanvasLoop() {

    Canvas(modifier = Modifier.padding(16.dp).size(200.dp).background(Color.Black)
        .border(1.dp, color = Color.DarkGray).clipToBounds()
        .pointerMotionEvents(onDown = { pointerInputChange: PointerInputChange ->
            //model.motionEvent.value = MotionEvent.Down
            pointerInputChange.consume()
        }, onMove = { pointerInputChange: PointerInputChange ->
            val dx = pointerInputChange.position.x - pointerInputChange.previousPosition.x
            val dy = pointerInputChange.position.y - pointerInputChange.previousPosition.y
            model.currentPosition.value += Offset(dx/16, dy/128)
            model.motionEvent.value = MotionEvent.Move
            model.refsresh.value++
            pointerInputChange.consume()
        }, onUp = { pointerInputChange: PointerInputChange ->
            //model.motionEvent.value = MotionEvent.Up
            pointerInputChange.consume()
        }, delayAfterDownInMillis = 25L
        )
    ) {

        model.refsresh.value

        ////////////////////
        //Прицел
        drawLine(
            color = Color.LightGray.copy(alpha = 0.5f),
            start = Offset(size.width / 2, 70.dp.toPx()),
            end = Offset(size.width / 2, size.height - 70.dp.toPx())
        , strokeWidth = 1.dp.toPx()
        )

        drawLine(
            color = Color.LightGray,
            start = Offset(70.dp.toPx(), size.height / 2),
            end = Offset(size.width - 70.dp.toPx(), size.height / 2)
            , strokeWidth = 1.dp.toPx()
        )
        ////////////////////

        //Рисуем сам сигнал
        val pointsSignal = model.createPointLoop(size).first

        drawPoints(
            color = Color.Green,
            points = pointsSignal,
            cap = StrokeCap.Round,
            pointMode = PointMode.Polygon,
            strokeWidth = 5f
        )

       //Рисуем кадрат
        drawPath(
            color = Color.Red,
            path = model.createPointLoop(size).four,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        )

        //Рисуем референс
        drawPath(
            color = Color.Blue,
            path = model.createPointLoop(size).third,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        )


    }


}