import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.generator2.editor.EditorMatModel
import com.example.generator2.editor.PaintingState
import com.smarttoolfactory.gesture.pointerMotionEvents

enum class MotionEvent {
    Idle, Down, Move, Up
}


val model = EditorMatModel()

@Composable
fun EditorCanvas() {
    var motionEvent by remember { mutableStateOf(MotionEvent.Idle) } // This is our motion event we get from touch motion
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) } // This is previous motion event before next touch is saved into this current position
    var currentPositionCorrection by remember { mutableStateOf(Offset.Unspecified) }

    // color and text are for debugging and observing state changes and position
    val gestureColor by remember { mutableStateOf(Color.Black) } //Цвет фона

    val drawModifier = Modifier.padding(16.dp) //.shadow(1.dp)
        //.fillMaxWidth()
        //.height(300.dp)
        //.clipToBounds()
        .fillMaxSize().background(gestureColor)
        .pointerMotionEvents(onDown = { pointerInputChange: PointerInputChange ->
            currentPosition = pointerInputChange.position
            motionEvent = MotionEvent.Down //gestureColor = Color.Blue
            pointerInputChange.consume()
        }, onMove = { pointerInputChange: PointerInputChange ->
            currentPosition = pointerInputChange.position
            motionEvent = MotionEvent.Move // gestureColor = Color.Green
            pointerInputChange.consume()
        }, onUp = { pointerInputChange: PointerInputChange ->
            motionEvent = MotionEvent.Up // gestureColor = Color.White
            pointerInputChange.consume()
        }, delayAfterDownInMillis = 25L
        )

    val path1 = remember { Path() }

    Box(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxWidth().aspectRatio(1.5f)
            .background(Color.Black)
    ) {

        Canvas(modifier = drawModifier) {

            model.sizeCanvas = size //Передали размер канвы

            when (motionEvent) {
                MotionEvent.Down -> {
                    currentPositionCorrection = Offset(currentPosition.x, currentPosition.y + 100f)
                    model.position = Offset(currentPosition.x, currentPosition.y - 200f)
                    model.lastPosition = model.position


                }

                MotionEvent.Move -> {
                    currentPositionCorrection = Offset(
                        currentPosition.x, currentPosition.y + 100f
                    ) // model.position = currentPosition
                    model.position = Offset(currentPosition.x, currentPosition.y - 200f)

                }

                MotionEvent.Up   -> { //path.lineTo(currentPosition.x, currentPosition.y)
                    //canvasText.append("MotionEvent.Up pos: $currentPosition\n")
                    currentPosition =
                        Offset.Unspecified //currentPositionCorrection = Offset( currentPosition.x, currentPosition.y + 100f)
                    motionEvent = MotionEvent.Idle
                }
                else             -> { //canvasText.append("MotionEvent.Idle\n")
                }
            }


            //Центральная вертикальная


            drawLine(
                color = Color.Gray,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height - 1)
            )
            drawLine(
                color = Color.Gray,
                start = Offset(size.width / 4, 0f),
                end = Offset(size.width / 4, size.height - 1)
            )
            drawLine(
                color = Color.Gray,
                start = Offset(size.width * 3 / 4, 0f),
                end = Offset(size.width * 3 / 4, size.height - 1)
            )

            //Горизонтальная линия
            drawLine(
                color = Color.Gray,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width - 1, size.height / 2)
            )
            drawLine(
                color = Color.Gray,
                start = Offset(0f, size.height / 4),
                end = Offset(size.width - 1, size.height / 4)
            )
            drawLine(
                color = Color.Gray,
                start = Offset(0f, size.height * 3 / 4),
                end = Offset(size.width - 1, size.height * 3 / 4)
            )

            drawRect(
                color = Color.DarkGray,
                topLeft = Offset(0f, 0f),
                size = size,
                style = Stroke(width = 1.dp.toPx())
            )

            if (currentPosition != Offset.Unspecified) {

                println("current position ${currentPosition.x}, ${currentPosition.y}")

                if (model.state == PaintingState.PaintPoint) {
                    model.line() //Расчет нового сигнала для точки
                }

                //Палец
                drawCircle(
                    color = Color.Red, center = currentPosition, radius = 60f, style = Stroke(
                        width = 3.dp.toPx(),
                        join = StrokeJoin.Bevel,
                        cap = StrokeCap.Square, //pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 15f))
                    )
                )

                drawCircle(
                    color = Color.Gray,
                    center = Offset(currentPosition.x, currentPosition.y - 200f),
                    radius = 10f,
                    alpha = 0.6f
                )


                val textPaint = Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    textSize = 24.sp.toPx()
                    color = android.graphics.Color.BLUE
                    typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
                }


                val paint = android.graphics.Paint()
                paint.textSize = 28f
                paint.color = 0xffff0000.toInt()
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        textConstrain(model.position.x.toInt(), model.position.y.toInt()),
                        currentPositionCorrection.x - 50f,
                        currentPositionCorrection.y - 350f,
                        paint
                    )
                }


            }


            //Рисуем сам сигнал
            val points3 = model.createPoint()
            drawPoints(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Red, Color.Yellow)
                ),
                points = points3,
                cap = StrokeCap.Round,
                pointMode = PointMode.Polygon,
                strokeWidth = 3f
            )


        }
    }
}

fun textConstrain(X: Int, Y: Int): String {
    var x = X
    var y = Y
    if (x < 0) x = 0

    if (x > 1023) x = 1023

    if (y < 0) y = 0

    if (y > 4095) y = 4095

    y = 4095 - y


    return "$x,$y"

}


