package dev.baseio.composeplayground.ui.animations.anmolverma.planetarysystem

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.baseio.common.LocalWindow
import dev.baseio.composeplayground.contributors.AnmolVerma
import dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh.NightSky
import kotlinx.coroutines.launch

@Composable
fun PlanetarySystem(modifier: Modifier) {

  val height = with(LocalDensity.current) {
    LocalWindow.current.screenHeightDp.toPx()
  }

  val centerOffset = with(LocalDensity.current) {
    Offset(
      LocalWindow.current.screenWidthDp.div(2).toPx(),
      LocalWindow.current.screenHeightDp.div(2).toPx()
    )
  }

  val infiniteTransition = rememberInfiniteTransition()
  val angleRotation by infiniteTransition.animateFloat(
    initialValue = 20f,
    targetValue = 45f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 1000, easing = FastOutLinearInEasing),
    )
  )

  val solarSystem by remember {
    mutableStateOf(SolarSystem(centerOffset))
  }

  val coroutineScope = rememberCoroutineScope()

  Surface(
    modifier
      .background(Color.Black)
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

      Box {
        NightSky(height, particleCount = 2500)
        angleRotation// hack to get the canvas called again!
        Canvas(modifier = Modifier
          .fillMaxSize(), onDraw = {
          solarSystem.planets.forEach {
            coroutineScope.launch {
              it.update()
            }
            // axis
            drawCircle(
              color = Color.Gray,
              center = centerOffset,
              radius = it.orbitRadius,
              style = Stroke(width = 2f)
            )

            //planet
            drawCircle(
              color = it.planetColor,
              center = Offset(it.x, it.y),
              radius = it.radius,
            )

            //moon
            drawCircle(
              color = Color.Gray,
              center = Offset(it.moon.x, it.moon.y),
              radius = 2f
            )
          }


        })
        CreatorBlock()
      }
    }
  }
}

@Composable
private fun PlanetBox(planetRadius: Float, planetX: Float, planetY: Float, color: Color) {

  Canvas(modifier = Modifier
    .fillMaxSize()
    .blur(2.dp), onDraw = {
    val offset = Offset(planetX, planetY)
    drawCircle(
      color = color,
      radius = planetRadius,
      center = offset
    )
  })
}

@Composable
private fun BoxScope.CreatorBlock() {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .align(Alignment.BottomCenter)
      .height(200.dp)
      .background(Color.Transparent)
  ) {
    AnmolVerma(Modifier.align(Alignment.Center))
  }
}