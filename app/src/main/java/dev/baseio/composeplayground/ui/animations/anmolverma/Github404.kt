package dev.baseio.composeplayground.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.R
import dev.baseio.composeplayground.contributors.AnmolVerma

@Composable
fun Github404(modifier: Modifier) {
  val infiniteTransition = rememberInfiniteTransition()
  val yFactor by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 30f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 2000, delayMillis = 0, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )

  val bgImageScaleFactor by infiniteTransition.animateFloat(
    initialValue = 1.3f,
    targetValue = 1.5f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 2000, delayMillis = 500, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )


  val width = with(LocalDensity.current) {
    LocalConfiguration.current.screenWidthDp.dp.toPx()
  }
  val height = with(LocalDensity.current) {
    LocalConfiguration.current.screenHeightDp.dp.toPx()
  }

  val githubAvatarX = remember {
    Animatable(width.div(2))
  }

  val githubAvatarY = remember {
    Animatable(height.div(2))
  }

  Box(
    modifier
      .background(MaterialTheme.colors.background)
  ) {

    Box(Modifier.fillMaxSize()) {
      BackgroundImageGithub(bgImageScaleFactor)
      Looking404(githubAvatarX.value, githubAvatarY.value.plus(yFactor))
      HomeTwo(githubAvatarX.value.plus(680), githubAvatarY.value.minus(220))
      HomeOne(githubAvatarX.value.plus(190), githubAvatarY.value.minus(180))
      SpaceShipShadow(
        githubAvatarX.value.plus(80),
        githubAvatarY.value.plus(180).minus(yFactor),
        yFactor
      )
      SpaceShip(githubAvatarX.value.plus(80), githubAvatarY.value.plus(30).minus(yFactor))
      AvatarShadow(githubAvatarX.value.minus(180), githubAvatarY.value.plus(210), yFactor)
      GithubAvatar(githubAvatarX.value.minus(180), githubAvatarY.value.minus(yFactor))
    }

    Box(
      modifier = Modifier
        .scale(0.6f)
        .align(Alignment.BottomEnd)
        .background(MaterialTheme.colors.background.copy(alpha = 0.4f))
    ) {
      AnmolVerma(Modifier.align(Alignment.Center))
    }
  }
}

@Composable
private fun HomeTwo(
  githubAvatarX: Float,
  githubAvatarY: Float
) {
  Image(
    painter = androidx.compose.ui.res.painterResource(id = R.drawable.deserthome1),
    contentDescription = null, modifier = Modifier
      .offset {
        IntOffset(
          githubAvatarX
            .toInt(),
          githubAvatarY
            .toInt()
        )
      }
      .scale(1.5f)
  )
}

@Composable
private fun HomeOne(
  githubAvatarX: Float,
  githubAvatarY: Float
) {
  Image(
    painter = androidx.compose.ui.res.painterResource(id = R.drawable.deserthome1),
    contentDescription = null, modifier = Modifier
      .offset {
        IntOffset(
          githubAvatarX
            .toInt(),
          githubAvatarY
            .toInt()
        )
      }
      .scale(2.8f)
  )
}

@Composable
private fun SpaceShip(
  githubAvatarX: Float,
  githubAvatarY: Float
) {
  Image(
    painter = androidx.compose.ui.res.painterResource(id = R.drawable.spaceship),
    contentDescription = null, modifier = Modifier
      .offset {
        IntOffset(
          githubAvatarX
            .toInt(),
          githubAvatarY
            .toInt()
        )
      }
      .scale(1.8f)
  )
}

@Composable
private fun SpaceShipShadow(
  githubAvatarX: Float,
  githubAvatarY: Float,
  yFactor: Float
) {
  Image(
    painter = androidx.compose.ui.res.painterResource(id = R.drawable.spshipshadow),
    contentDescription = null, modifier = Modifier
      .offset {
        IntOffset(
          githubAvatarX
            .toInt(),
          githubAvatarY
            .toInt()
        )
      }
      .scale(1.8f + yFactor.times(0.02f))
  )
}

@Composable
private fun Looking404(
  githubAvatarX: Float,
  githubAvatarY: Float
) {
  Image(
    painter = androidx.compose.ui.res.painterResource(id = R.drawable.notfound),
    contentDescription = null, modifier = Modifier
      .offset {
        IntOffset(
          githubAvatarX
            .minus(480)
            .toInt(),
          githubAvatarY
            .toInt()
            .minus(130)
        )
      }
      .scale(1.8f)
  )
}

@Composable
fun GithubAvatar(
  githubAvatarX: Float,
  githubAvatarY: Float
) {
  Image(
    painter = androidx.compose.ui.res.painterResource(id = R.drawable.githubavatar),
    contentDescription = null, modifier = Modifier
      .offset {
        IntOffset(
          githubAvatarX
            .toInt(),
          githubAvatarY.toInt()
        )
      }
      .scale(1.8f)
  )
}

@Composable
private fun AvatarShadow(
  githubAvatarX: Float,
  githubAvatarY: Float,
  yFactor: Float
) {
  Image(
    painter = androidx.compose.ui.res.painterResource(id = R.drawable.avatarshadow),
    contentDescription = null, modifier = Modifier
      .offset {
        IntOffset(
          githubAvatarX
            .toInt(),
          githubAvatarY.toInt()
        )
      }
      .scale(1.8f + yFactor.times(0.02f))
  )
}

@Composable
fun BackgroundImageGithub(bgImageScaleFactor: Float) {
  Image(
    painter = androidx.compose.ui.res.painterResource(id = R.drawable.background),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier
      .fillMaxSize()
      .scale(bgImageScaleFactor)
  )
}