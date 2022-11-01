package dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingPulse(color: Color = Color.Black, size: Int = 50, speed: Double = 0.5) {
    val timing = speed * 4

    val maxCounter = 3

    Row(
        Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(maxCounter) { index ->
            CirclePulse(size, color, timing, index)
        }
    }
}

@Composable
private fun CirclePulse(size: Int, color: Color, timing: Double, index: Int) {

    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = timing.times(1000).toInt(),
                delayMillis = (index * timing / 3).toInt(),
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    val opacity by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = timing.times(1000).toInt(),
                delayMillis = (index * timing / 3).toInt(),
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )


    Box(
        modifier = Modifier
            .size(size.dp)
            .scale(scale)
            .alpha(opacity)
            .background(color, shape = CircleShape)
    )
}