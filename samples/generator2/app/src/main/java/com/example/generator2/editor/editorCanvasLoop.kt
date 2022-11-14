import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.gesture.pointerMotionEvents

@Composable
fun EditorCanvasLoop() {

    Canvas(modifier = Modifier.padding(16.dp).size(200.dp).background(Color.Black)
        .border(1.dp, color = Color.DarkGray)
        .pointerMotionEvents(onDown = { pointerInputChange: PointerInputChange ->
            //model.motionEvent.value = MotionEvent.Down
            pointerInputChange.consume()
        }, onMove = { pointerInputChange: PointerInputChange ->
            val dx = pointerInputChange.position.x - pointerInputChange.previousPosition.x
            val dy = pointerInputChange.position.y - pointerInputChange.previousPosition.y
            model.currentPosition.value += Offset(dx/6, dy/6)
            model.motionEvent.value = MotionEvent.Move
            pointerInputChange.consume()
        }, onUp = { pointerInputChange: PointerInputChange ->
            //model.motionEvent.value = MotionEvent.Up
            pointerInputChange.consume()
        }, delayAfterDownInMillis = 25L
        )
    ) {

        drawLine(
            color = Color.Gray,
            start = Offset(size.width / 2, 0f),
            end = Offset(size.width / 2, size.height - 1)
        )

        drawLine(
            color = Color.Gray,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width - 1, size.height / 2)
        )


        //Рисуем сам сигнал
        val pointsV = model.createPointLoop(size).second
        drawPoints(
            brush = Brush.linearGradient(
                colors = listOf(Color.Blue, Color.Blue)
            ),
            points = pointsV,
            cap = StrokeCap.Round,
            pointMode = PointMode.Lines,
            strokeWidth = 6f
        )

        //Рисуем сам сигнал
        val pointsH = model.createPointLoop(size).third
        drawPoints(
            brush = Brush.linearGradient(
                colors = listOf(Color.Blue, Color.Blue)
            ),
            points = pointsH,
            cap = StrokeCap.Round,
            pointMode = PointMode.Lines,
            strokeWidth = 6f
        )

        //Рисуем сам сигнал
        val pointsSignal = model.createPointLoop(size).first
        drawPoints(
            brush = Brush.linearGradient(
                colors = listOf(Color.Green, Color.Green)
            ),
            points = pointsSignal,
            cap = StrokeCap.Round,
            pointMode = PointMode.Points,
            strokeWidth = 3f
        )

        //Рисуем сам сигнал
        val pointsNull = model.createPointLoop(size).four
        drawPoints(
            brush = Brush.linearGradient(
                colors = listOf(Color.Magenta, Color.Yellow)
            ),
            points = pointsNull,
            cap = StrokeCap.Round,
            pointMode = PointMode.Lines,
            strokeWidth = 4f
        )


    }


}