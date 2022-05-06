package dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingFiveLinesCenter(color: Color = Color.Black, size: Int = 50, speed: Double = 0.5) {
    val maxCounter = 5

    Row(Modifier.height(size.dp), verticalAlignment = Alignment.CenterVertically) {
        repeat(maxCounter) { index ->
            EachRect(size, index, color, speed)
            Spacer(modifier = Modifier.width(size.div(10).dp))
        }
    }

}

@Composable
private fun EachRect(
    size: Int,
    index: Int,
    color: Color,
    speed: Double
) {
    val timing = speed.times(1000)
    val delay = (if (index == 2) 0.0 else if (index == 1 || index == 3) (timing / 3) else (timing / 3) * 2).toInt()
    val animateInifinite = rememberInfiniteTransition()
    val height by animateInifinite.animateFloat(
        initialValue = (size / 3).toFloat(),
        targetValue = size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = timing.toInt(),
                delayMillis = delay,
                easing = LinearEasing
            ), repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .height(height.dp)
            .width(6.dp)
            .background(color, shape = RoundedCornerShape(25))
    )
}