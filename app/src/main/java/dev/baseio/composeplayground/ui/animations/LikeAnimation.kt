package dev.baseio.composeplayground.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LikeAnimation(modifier: Modifier) {

  val ringScale = remember {
    Animatable(0f)
  }
  val ringOpacity = remember {
    Animatable(1f)
  }
  val imageScale = remember {
    Animatable(1f)
  }

  val imageRotation = remember {
    Animatable(0f)
  }

  val orbOffset = remember {
    Animatable(0f)
  }

  val orbScale = remember {
    Animatable(0f)
  }

  val color = remember {
    mutableStateOf(Color.Gray)
  }

  val coroutineScope = rememberCoroutineScope()

  Surface(
    modifier
      .background(MaterialTheme.colors.onBackground)
  ) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Button(onClick = {
        coroutineScope.launch {
          ringScale.snapTo(0f)
          ringOpacity.snapTo(1f)
          imageScale.snapTo(1f)
          imageRotation.snapTo(0f)
          orbOffset.snapTo(0f)
          orbScale.snapTo(0f)
          color.value = Color.Gray
        }


      }) {
        Text(text = "Reset Animation!")
      }
      Spacer(modifier = Modifier.height(28.dp))
      Column(
        Modifier
          .clickable {
            coroutineScope.launch {
              orbOffset.animateTo(0f)
              ringOpacity.animateTo(1f)
              ringScale.animateTo(0f)
              imageScale.animateTo(0.6f, tween(300, easing = LinearEasing))
              imageRotation.animateTo(-30f, tween(300, easing = LinearEasing))
            }

            coroutineScope.launch {
              delay(300)
              coroutineScope.launch {
                orbOffset.animateTo(orbOffset.value.minus(50f), tween(600))
              }
              color.value = green
              coroutineScope.launch {
                orbScale.animateTo(1f)
                ringScale.animateTo(1f)
                imageScale.animateTo(1f)
                ringOpacity.animateTo(0f)
                imageRotation.animateTo(0f)
              }
            }

            coroutineScope.launch {
              delay(600)
              orbScale.animateTo(0f, tween(300))
            }
          }
          .padding(4.dp)
      ) {

        val offsetEven = with(LocalDensity.current) {
          orbOffset.value.toDp()
        }
        val offsetOdd = with(LocalDensity.current) {
          orbOffset.value.toDp()
            .div(1.2.dp)
        }

        Box {
          for (item in 0 until 8) {
            Canvas(
              modifier = Modifier
                .align(Alignment.Center)
                .rotate(item.times(45f))
                .offset(x = 0.dp, y = (if (item % 2 == 0) offsetEven else offsetOdd.dp))
                .scale(orbScale.value),
              onDraw = {
                drawCircle(
                  color = yellow,
                  alpha = if (item % 2 == 0) 1f else 0.5f,
                  radius = if (item % 2 == 0) 15f else 5f
                )
              })
          }


          Canvas(modifier = Modifier
            .size(100.dp)
            .align(Alignment.Center)
            .scale(ringScale.value), onDraw = {
            drawCircle(
              color = Color.Gray.copy(alpha = ringOpacity.value),
              style = Stroke(width = 8f)
            )
          })

          Icon(
            painter = painterResource(id = R.drawable.ic_thumb),
            contentDescription = null,
            tint = color.value, modifier = Modifier
              .align(Alignment.Center)
              .size(50.dp)
              .scale(imageScale.value)
              .rotate(imageRotation.value)
          )
        }
      }
    }
  }
}