import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.generator2.editor.EditorMatModel
import com.smarttoolfactory.gesture.pointerMotionEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    var gestureColor by remember { mutableStateOf(Color.White) }


    // 🔥🔥 If pointer is moved fast, Canvas misses, only Canvas misses it, events work fine
    // MotionEvent.Down events, and skips first down
    val scope = rememberCoroutineScope()



    val drawModifier = Modifier //.padding(8.dp)
        //.shadow(1.dp)
        //.fillMaxWidth()
        //.height(300.dp)
        //.clipToBounds()
        .fillMaxSize().background(gestureColor)
        .pointerMotionEvents(onDown = { pointerInputChange: PointerInputChange ->
            currentPosition = pointerInputChange.position
            motionEvent = MotionEvent.Down
            gestureColor = Color.Blue
            pointerInputChange.consume()
        }, onMove = { pointerInputChange: PointerInputChange ->
            currentPosition = pointerInputChange.position
            motionEvent = MotionEvent.Move
            gestureColor = Color.Green
            pointerInputChange.consume()
        }, onUp = { pointerInputChange: PointerInputChange ->
            motionEvent = MotionEvent.Up
            gestureColor = Color.White
            pointerInputChange.consume()
        }, delayAfterDownInMillis = 25L
        )


    val path1 = remember { Path() }

    //currentPosition = Offset(
    //    10f,
    //    10f
    //) //currentPositionCorrection = Offset( currentPosition.x, currentPosition.y + 100f)

    Box(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxWidth().aspectRatio(1f)
            .background(Color.White)
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
                        currentPosition.x,
                        currentPosition.y + 100f
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



            path1.reset()


//            if (currentPosition != Offset.Unspecified) drawLine(
//                start = Offset(x = currentPosition.x, y = currentPosition.y),
//                end = Offset(x = 0f, y = 0f),
//                color = Color.Magenta
//            )





            if (currentPosition != Offset.Unspecified) {

                println("current position ${currentPosition.x}, ${currentPosition.y}")

                model.calculateBox() //Расчет нового сигнала

                //Палец
                drawCircle(
                    color = Color.Red, center = currentPosition, radius = 60f, style = Stroke(
                        width = 3.dp.toPx(),
                        join = StrokeJoin.Bevel,
                        cap = StrokeCap.Square, //pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 15f))
                    )
                )

                drawCircle(
                    color = Color.Black,
                    center = Offset(currentPosition.x, currentPosition.y - 200f),
                    radius = 10f,
                    alpha = 0.6f
                )

            }


            val points3 = model.createPoint(size)

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



