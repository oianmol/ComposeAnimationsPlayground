package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import dev.baseio.composeplayground.R

val discBackground = Color(229, 236, 229)

val colorRedStart = Color(230, 100, 91)

val innerDisc = Color(52, 46, 45)

@Composable
fun GramophoneDisc() {

  val screenHeight = LocalConfiguration.current.screenHeightDp
  val screenWidth = LocalConfiguration.current.screenWidthDp.dp
  val gramophoneSize = screenHeight.dp / 3

  val centerOffset = DpOffset(screenWidth.div(2), screenHeight.dp.div(2))

  val xOffset = centerOffset.x.plus(gramophoneSize.div(4))
  val yOffset = centerOffset.y.minus(gramophoneSize.div(2))


  val infiniteRotate = rememberInfiniteTransition()
  val rotateAnimation by infiniteRotate.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = InfiniteRepeatableSpec(
      tween(durationMillis = 6000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    )
  )

  Box(
    modifier = Modifier
      .background(discBackground)
  ) {


    AlbumArtBackground()
    BlurredAlbumArt()
    OuterRing(xOffset, yOffset, gramophoneSize, rotateAnimation)
    InnerRing(xOffset, yOffset, gramophoneSize, rotateAnimation)
    AlbumSongsList()
  }
}

@Composable
private fun AlbumArtBackground() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(colorRedStart)
  )
}

@Composable
fun AlbumSongsList(
) {
  val albumHeight = LocalDensity.current.run { 48.dp.toPx() }
  CircularList(
    modifier = Modifier.fillMaxSize(),
    circularFraction = -0.50f,
  ) {
    repeat(10) {
      Album()
    }
  }
}

@Composable
fun Album() {
  GlideImage(
    imageModel = "https://wpimg.pixelied.com/blog/wp-content/uploads/2021/06/15134504/Spotify-Cover-Art-with-Text-Aligned-480x480.png",
    contentDescription = null,
    modifier = Modifier,
  )
}

@Composable
private fun BlurredAlbumArt() {
  GlideImage(
    imageModel = "https://wpimg.pixelied.com/blog/wp-content/uploads/2021/06/15134504/Spotify-Cover-Art-with-Text-Aligned-480x480.png",
    contentDescription = null, modifier = Modifier
      .fillMaxSize()
      .blur(16.dp),
    previewPlaceholder = R.drawable.ic_launcher_background
  )
}

@Composable
private fun InnerRing(
  xOffset: Dp,
  yOffset: Dp,
  gramophoneSize: Dp,
  rotateAnimation: Float
) {
  GlideImage(
    imageModel = "https://wpimg.pixelied.com/blog/wp-content/uploads/2021/06/15134504/Spotify-Cover-Art-with-Text-Aligned-480x480.png",
    contentDescription = null, modifier = Modifier
      .width(gramophoneSize)
      .height(gramophoneSize)
      .offset(x = xOffset, yOffset)
      .clip(
        shape = CircleShape
      )
      .rotate(rotateAnimation), previewPlaceholder = R.drawable.ic_launcher_background
  )
}

@Composable
private fun OuterRing(
  xOffset: Dp,
  yOffset: Dp,
  gramophoneSize: Dp,
  rotateAnimation: Float
) {
  Surface(
    modifier = Modifier
      .offset(x = xOffset, y = yOffset)
      .size(gramophoneSize)
      .scale(2.3f)
      .rotate(rotateAnimation),
    elevation = 8.dp,
    shape = CircleShape,
    color = Color.Transparent,
    contentColor = Color.Transparent
  ) {
    Box(
      modifier = Modifier
        .background(
          Brush.radialGradient(
            colorStops = arrayOf(
              Pair(0f, innerDisc.copy(alpha = 0.8f)),
              Pair(0.2f, innerDisc.copy(alpha = 0.5f)),
              Pair(0.4f, innerDisc.copy(alpha = 0.6f)),
              Pair(0.6f, innerDisc.copy(alpha = 0.8f)),
              Pair(0.8f, innerDisc.copy(alpha = 0.95f)),
              Pair(1f, innerDisc.copy(alpha = 1f)),
            )
          ),
          shape = CircleShape
        )
    )
  }


}