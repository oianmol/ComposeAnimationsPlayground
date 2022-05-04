package dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingText(color: Color = Color.Black, size: Int = 50, speed: Double = 0.5) {

    val loadingText = "Loading..."
    val maxCounter = loadingText.length
    var counter by remember {
        mutableStateOf(0)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = speed, block = {
        scope.launch {
            while (true) {
                delay(speed.times(800).toLong())
                counter = if (counter == (maxCounter - 1)) 0 else counter + 1
            }
        }
    })

    Row(Modifier, verticalAlignment = Alignment.CenterVertically) {
        loadingText.forEachIndexed { index, c ->
            LoadingCharAtString(size = size, counter = counter, index = index, color = color,text = c)
        }
    }
}

@Composable
fun LoadingCharAtString(
    size: Int,
    counter: Int,
    index: Int,
    color: Color,
    text : Char
) {
    val verticalCharAnimation = animateIntAsState(targetValue = if (counter == index) -size / 10 else 0)

    Text(
        modifier = Modifier.offset(
            x = 0.dp,
            y = verticalCharAnimation.value.dp
        ),
        text = text.toString(),
        color = color,
        fontSize = size.sp
    )
}
