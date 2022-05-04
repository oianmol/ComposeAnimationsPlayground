package dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingThreeBallsRotation(color: Color = Color.Black, size: Int = 50, speed: Double = 0.5) {
    val timing = speed * 1.5

    val maxCounter = 3
    val infiniteTransition = rememberInfiniteTransition()
    val isAnimating by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = timing.times(1000).toInt(), delayMillis = 0, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Row(Modifier.rotate(isAnimating), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(maxCounter) { index ->
            Box(
                modifier = Modifier
                    .size(size.dp)
                    .background(color, shape = CircleShape)
            )
        }
    }
}