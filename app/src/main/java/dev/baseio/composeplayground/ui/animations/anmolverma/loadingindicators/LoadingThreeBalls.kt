package dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingThreeBalls(color: Color = Color.Black, size: Int = 50, speed: Double = 0.5) {
    val maxCounter = 3
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
            EachCircle(size, counter, index, color)
        }
    }
}

@Composable
private fun EachCircle(
    size: Int,
    counter: Int,
    index: Int,
    color: Color
) {
    val scale by animateFloatAsState(targetValue = ((if (counter == index) 1.0 else 0.5).toFloat()))

    Box(
        modifier = Modifier
            .size(size.dp)
            .scale(scale)
            .background(color, shape = CircleShape)
    )
}