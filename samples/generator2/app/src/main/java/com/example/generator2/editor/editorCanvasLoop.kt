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

        //Вертикальные ограничения
        val pointsV = model.createPointLoop(size).second
        drawPoints(
            color = Color.Gray,
            points = pointsV,
            cap = StrokeCap.Round,
            pointMode = PointMode.Lines,
            strokeWidth = 3f
        )

        //Горизонтальное ограничение
        val pointsH = model.createPointLoop(size).third
        drawPoints(
            color = Color.Gray,
            points = pointsH,
            cap = StrokeCap.Round,
            pointMode = PointMode.Lines,
            strokeWidth = 3f
        )

        //Рисуем сам сигнал
        val pointsSignal = model.createPointLoop(size).first
        drawPoints(
            color = Color.Green,
            points = pointsSignal,
            cap = StrokeCap.Round,
            pointMode = PointMode.Points,
            strokeWidth = 4f
        )

//        //Рисуем сам сигнал
//        val pointsNull = model.createPointLoop(size).four
//        drawPoints(
//            brush = Brush.linearGradient(
//                colors = listOf(Color.Magenta, Color.Yellow)
//            ),
//            points = pointsNull,
//            cap = StrokeCap.Round,
//            pointMode = PointMode.Lines,
//            strokeWidth = 4f
//        )


    }


}