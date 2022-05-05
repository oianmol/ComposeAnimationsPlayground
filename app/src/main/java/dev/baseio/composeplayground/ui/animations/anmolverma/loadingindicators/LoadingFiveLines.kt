package dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingFiveLines(color: Color = Color.Black, size: Int = 50, speed: Double = 0.5) {
    val maxCounter = 5
    val timing = speed / 2

    var counter by remember {
        mutableStateOf(0)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = speed, block = {
        scope.launch {
            while (true) {
                delay(timing.times(1000).toLong())
                counter = if (counter == maxCounter - 1) 0 else counter + 1.also { counter = it }
            }
        }
    })

    Row(Modifier, verticalAlignment = Alignment.CenterVertically) {
        repeat(maxCounter) { index ->
            EachRect(size, counter, index, color)
            Spacer(modifier = Modifier.width(size.div(10).dp))
        }
    }

}

@Composable
private fun EachRect(
    size: Int,
    counter: Int,
    index: Int,
    color: Color
) {
    val height by animateFloatAsState(
        targetValue = ((if (counter == index) size / 3 else size).toFloat()),
        animationSpec = tween(easing = LinearOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .height(height.dp)
            .width(6.dp)
            .background(color, shape = RoundedCornerShape(25))
    )
}