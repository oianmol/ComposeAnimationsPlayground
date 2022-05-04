package dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingThreeBallsBouncing(color: Color, size: Int, speed: Double) {
    val maxCounter = 3
    val frame = Offset(x = size.toFloat(), y = size.toFloat() )
    var counter by remember {
        mutableStateOf(0)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = speed, block = {
        scope.launch {
            while (true) {
                delay(speed.times(1000).toLong())
                counter = if (counter == (maxCounter - 1)) 0 else counter + 1
            }
        }
    })

    Row(Modifier, verticalAlignment = Alignment.CenterVertically) {
        repeat(maxCounter) { index ->
            EachBouncingCircle(size, counter, index, color,frame)
        }
    }
}

@Composable
fun EachBouncingCircle(size: Int, counter: Int, index: Int, color: Color, frame : Offset) {
    val bounce by animateDpAsState(targetValue = ((if (counter == index) -frame.y / 5 else frame.y / 5).dp))

    Box(
        modifier = Modifier
            .size(size.dp)
            .offset(
                x = 0.dp,
                y = bounce
            )
            .background(color, shape = CircleShape)
    )
}
