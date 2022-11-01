package dev.baseio.composeplayground.ui.animations

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import dev.baseio.composeplayground.contributors.AnmolVerma
import dev.baseio.composeplayground.ui.animations.anmolverma.netflixanim.EffectBrush
import dev.baseio.composeplayground.ui.animations.anmolverma.netflixanim.EffectLumieres
import kotlinx.coroutines.launch

val baseColor = Color(0xffe40913)

/**
 * Inspiration
 * https://dev.to/claudiobonfati/netflix-intro-animation-pure-css-1m0c
 */
@Composable
fun NetflixIntroAnimation() {

  // body
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.Black)
  ) {
    //container
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.Black),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    )
    {
      //netflix intro
      NetflixIntro(Modifier)
    }


    Box(
      modifier = Modifier
        .align(Alignment.BottomEnd)
    ) {
      AnmolVerma(
        Modifier
          .padding(24.dp)
          .align(Alignment.Center)
      )
    }

  }
}

@Composable
fun NetflixIntro(modifier: Modifier) {
  val zoomInNetflixBox = remember {
    Animatable(1f)
  }

  val fadingLumieresBox = remember {
    Animatable(baseColor.copy(alpha = 0.5f))
  }


  val showingLumieres = remember {
    Animatable(0f)
  }

  val brushMovingbrush1 = remember {
    Animatable(0f)
  }

  val brushMovingbrush3 = remember {
    Animatable(0f)
  }

  val brushMovingbrush2 = remember {
    Animatable(0f)
  }

  LaunchedEffect(key1 = true, block = {
    launch {
      zoomInNetflixBox.animateTo(16f, animationSpec = keyframes {
        durationMillis = 3500
        delayMillis = 500
        1f at 0 with LinearEasing
        5f at 750 with LinearEasing
        10f at 1750 with LinearEasing
        16f at 3500 with LinearEasing
      })
    }
    launch {
      fadingLumieresBox.animateTo(
        targetValue = baseColor.copy(alpha = 0f),
        animationSpec = keyframes {
          durationMillis = 2000
          delayMillis = 600
          baseColor.copy(alpha = 0.5f) at 0 with LinearEasing
          baseColor.copy(alpha = 0f) at 2500 with LinearEasing
        })
    }
    launch {
      brushMovingbrush1.animateTo(targetValue = -100f, animationSpec = keyframes {
        durationMillis = 3500
        delayMillis = 1200
        0f at 0 with LinearEasing
        -100f at 3500 with LinearEasing
      })
    }

    launch {
      brushMovingbrush3.animateTo(targetValue = -100f, animationSpec = keyframes {
        durationMillis = 2500
        delayMillis = 800
        0f at 0 with LinearEasing
        -100f at 2500 with LinearEasing
      })
    }

    launch {
      brushMovingbrush2.animateTo(targetValue = -100f, animationSpec = keyframes {
        durationMillis = 2500
        delayMillis = 500
        0f at 0 with LinearEasing
        -100f at 2500 with LinearEasing
      })
    }

    launch {
      showingLumieres.animateTo(1f, keyframes {
        durationMillis = 2500
        delayMillis = 1600
        0f at 0 with LinearEasing
        1f at 2500 with LinearEasing
      })
    }
  })

  // netflix intro

  val nWidth = with(LocalDensity.current) {
    300f.toDp()
  }

  val nHeight = with(LocalDensity.current) {
    300f.toDp()
  }

  //letter N
  Box(
    modifier = modifier
      .width(nWidth)
      .height(nHeight)
      .graphicsLayer(
        scaleX = zoomInNetflixBox.value, scaleY = zoomInNetflixBox.value,
      )
  ) {
    EffectBrushOne(
      modifier = Modifier
        .fillMaxWidth(0.195f)
        .fillMaxHeight()
        .offset(x = (22.4 / 100).times(nWidth), y = 0.dp)
        .rotate(180f)
        .background(fadingLumieresBox.value),
      brushMovingbrush1,
      showingLumieres, nWidth
    )

    EffectBrushTwo(
      modifier = Modifier
        .fillMaxWidth(0.19f)
        .fillMaxHeight()
        .offset(x = (57.8 / 100).times(nWidth), y = 0.dp)
        .rotate(180f), brushMovingbrush2,nWidth
    )
    EffectBrushThree(
      modifier = Modifier
        .fillMaxWidth(0.19f)
        .fillMaxHeight(1.5f)
        .offset(x = (40.5 / 100).times(nWidth), y = (-25 / 100).times(nHeight))
        .rotate(-19.5f), brushMovingbrush3,nWidth
    )
  }


}

@Composable
fun EffectBrushTwo(
  modifier: Modifier,
  brushMovingbrush: Animatable<Float, AnimationVector1D>,
  nWidth: Dp
) {
  EffectBrush(brushMovingbrush, modifier, nWidth)
}


@Composable
fun EffectBrushThree(
  modifier: Modifier,
  brushMoving: Animatable<Float, AnimationVector1D>,
  nWidth: Dp
) {
  EffectBrush(brushMoving, modifier,nWidth)
}

@Composable
fun EffectBrushOne(
  modifier: Modifier = Modifier,
  brushMoving: Animatable<Float, AnimationVector1D>,
  showingLumieres: Animatable<Float, AnimationVector1D>,
  nWidth: Dp
) {
  EffectBrush(brushMoving, modifier, nWidth)

  EffectLumieres(
    showingLumieres, Modifier
      .fillMaxWidth(0.195f)
      .fillMaxHeight()
      .offset(x = (22.4 / 100).times(nWidth), y = 0.dp)
  )
}

