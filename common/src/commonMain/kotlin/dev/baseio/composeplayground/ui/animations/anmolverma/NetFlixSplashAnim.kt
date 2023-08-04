package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp

val netflixColor = Color(0xffe40913)

@Composable
fun NetFlixSplashAnim() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .layoutId("root")
    ) {
        // N
        Box(Modifier.align(Alignment.Center).height(60.dp)) {
            Row {
                DrawN()
                Spacer(Modifier.width(10.dp))
                DrawF(isE = true)
                Spacer(Modifier.width(10.dp))
                DrawT()
                Spacer(Modifier.width(10.dp))
                DrawF()
            }
        }
    }
}

@Composable
fun DrawF(isE: Boolean = false) {
    Row {
        // E
        Block(
            Modifier.width(15.dp)
                .fillMaxHeight()
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Block(
                Modifier.width(20.dp)
                    .height(10.dp)
            )
            Block(
                Modifier.width(20.dp)
                    .height(10.dp)
            )
            Block(
                Modifier.width(20.dp)
                    .height(10.dp), color = if (isE) netflixColor else Color.Black

            )
        }
    }
}

@Composable
fun DrawT() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // T
        Block(
            Modifier.width(30.dp)
                .height(10.dp)
        )
        Block(
            Modifier.width(10.dp)
                .fillMaxHeight()
        )
    }
}

@Composable
private fun DrawN() {
    Row {
        // N
        Block(
            Modifier.width(15.dp)
                .fillMaxHeight()
        )
        Block(
            Modifier.width(15.dp)
                .fillMaxHeight()
                .graphicsLayer {
                    this.rotationZ = 155f
                }
        )
        Block(
            Modifier.width(15.dp)
                .fillMaxHeight()
        )
    }
}

@Composable
fun Block(modifier: Modifier, color: Color = netflixColor) {
    Box(
        modifier.background(color)
    ) {

    }
}