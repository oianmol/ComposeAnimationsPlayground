package dev.baseio.composeplayground.ui.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.contributors.AnmolVerma
import dev.baseio.composeplayground.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

val bgColor = Color(20, 47, 65)
val greenAndroidColor = Color(113, 217, 140)
val blueColor = Color(83, 132, 237)

/**
 * https://twitter.com/androiddev/status/1504564052026081286?s=12
 */

@Composable
fun AndroidMadSkills() {
  val widthDP = LocalConfiguration.current.screenWidthDp.dp
  val heightDP = LocalConfiguration.current.screenHeightDp.dp

  val widthPX = with(LocalDensity.current) {
    widthDP.toPx()
  }
  val heightPX = with(LocalDensity.current) {
    heightDP.toPx()
  }

  val ovalSizePx = with(LocalDensity.current) {
    200.dp.toPx()
  }

  val firstOvalX = remember {
    Animatable(0f.minus(ovalSizePx.times(2)))
  }
  val firstOvalY = remember {
    Animatable(heightPX.div(2).minus(ovalSizePx.div(2)))
  }

  val ovalWidth = remember {
    Animatable(ovalSizePx.times(2))
  }
  val ovalHeight = remember {
    Animatable(ovalSizePx)
  }

  val cornerRadiusX = remember {
    Animatable(400f)
  }

  val cornerRadiusY = remember {
    Animatable(360f)
  }


  val secondOvalX = remember {
    Animatable(widthPX)
  }
  val secondOvalY = remember {
    Animatable(heightPX.div(2).minus(ovalSizePx.div(2)))
  }

  val makeMad = remember {
    mutableStateOf(false)
  }

  val makeMadAlpha = remember {
    Animatable(0f)
  }

  // frame 2
  val madTextX = remember {
    Animatable((widthPX.times(0.01f)))
  }

  val madTextY = remember {
    Animatable(heightPX.div(2))
  }

  val ltadX = remember {
    Animatable(widthPX.times(0.05f))
  }

  val ltadY = remember {
    Animatable(heightPX.plus(100f))
  }

  val alphaLTADColumn = remember {
    Animatable(0f)
  }

  val madTextScale = remember {
    Animatable(1f)
  }

  val animationScope = rememberCoroutineScope()

  val showCreator = remember {
    mutableStateOf(false)
  }


  LaunchedEffect(key1 = true, block = {
    delay(5000)
    firstFrameFirstJob(
      widthPX,
      ovalSizePx,
      animationScope,
      firstOvalX,
      secondOvalX,
      ovalWidth,
      ovalHeight, cornerRadiusX, cornerRadiusY
    )
    delay(200)
    firstFrameSecondJob(
      animationScope,
      firstOvalX,
      ovalSizePx,
      secondOvalX,
      widthPX,
      ovalWidth,
      ovalHeight, cornerRadiusX, cornerRadiusY
    ) {
      secondFrameJob(
        madTextX,
        madTextY,
        animationScope,
        (widthPX.times(0.05f)),
        widthPX.times(0.2f),
        makeMad,
        madTextScale,
        heightPX.div(4.5f),
        makeMadAlpha,
        ltadY,
        heightPX.div(2.8f),
        alphaLTADColumn,

        )

      secondFrameCirclesJob(
        firstOvalX,
        firstOvalY,
        secondOvalX,
        secondOvalY,
        ovalHeight,
        ovalWidth,
        ovalSizePx, widthPX, heightPX, animationScope,showCreator
      )
    }

  })


  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(bgColor)
  ) {
    FrameTwo(
      textX = madTextX,
      textY = madTextY,
      ltadX,
      ltadY,
      madTextScale,
      makeMad.value,
      makeMadAlpha, alphaLTADColumn
    )
    FrameOne(
      firstOvalX,
      firstOvalY,
      cornerRadiusX,
      cornerRadiusY,
      ovalWidth,
      ovalHeight,
      secondOvalX,
      secondOvalY
    )

    if (showCreator.value) {
      Box(
        modifier = Modifier
          .align(Alignment.TopEnd)
      ) {
        AnmolVerma(Modifier.padding(24.dp).align(Alignment.Center))
      }
    }
  }
}

fun secondFrameCirclesJob(
  firstOvalX: Animatable<Float, AnimationVector1D>,
  firstOvalY: Animatable<Float, AnimationVector1D>,
  secondOvalX: Animatable<Float, AnimationVector1D>,
  secondOvalY: Animatable<Float, AnimationVector1D>,
  ovalHeight: Animatable<Float, AnimationVector1D>,
  ovalWidth: Animatable<Float, AnimationVector1D>,
  ovalSizePx: Float,
  widthPX: Float,
  heightPX: Float,
  animationScope: CoroutineScope,
  showCreator: MutableState<Boolean>,
) {
  animationScope.launch {
    animationScope.launch {
      firstOvalX.snapTo(widthPX.times(2.2f))
      firstOvalY.snapTo(heightPX.times(-0.2f))
      secondOvalX.snapTo(widthPX.times(-0.6f))
      secondOvalY.snapTo(heightPX.times(-0.8f))
      delay(500)
      firstOvalX.animateTo(widthPX.times(0.8f))
      firstOvalY.animateTo(heightPX.times(0.2f))
      secondOvalX.animateTo(widthPX.times(0.6f))
      secondOvalY.animateTo(heightPX.times(0.8f))
      animationScope.launch {
        ovalHeight.animateTo(ovalSizePx.times(2))
        ovalWidth.animateTo(ovalSizePx)
        secondOvalY.animateTo(heightPX.times(0.4f), tween(durationMillis = 500, delayMillis = 400))
        showCreator.value = true
      }
    }

  }
}

fun secondFrameJob(
  madTextX: Animatable<Float, AnimationVector1D>,
  madTextY: Animatable<Float, AnimationVector1D>,
  animationScope: CoroutineScope,
  endXText: Float,
  towardsCenter: Float,
  makeMad: MutableState<Boolean>,
  madTextScale: Animatable<Float, AnimationVector1D>,
  endY: Float,
  makeMadAlpha: Animatable<Float, AnimationVector1D>,
  ltadY: Animatable<Float, AnimationVector1D>,
  ltadYEnd: Float,
  alphaColumn: Animatable<Float, AnimationVector1D>,

  ) {
  animationScope.launch {
    makeMadAlpha.animateTo(1f, tween(durationMillis = 300))
    madTextX.animateTo(towardsCenter, tween(durationMillis = 1000))
    delay(200)
    makeMad.value = true
    madTextX.animateTo(endXText)
    animationScope.launch {
      makeMadAlpha.animateTo(0f, tween(1000))
    }
    madTextScale.animateTo(1.2f, tween(1000))
    madTextY.animateTo(endY, tween(1000))
    // we make the text MAD and then append Skills to it.
    delay(200)
    animationScope.launch {
      alphaColumn.animateTo(1f, tween(800))
    }
    ltadY.animateTo(ltadYEnd, tween(200))
    delay(400)
    alphaColumn.animateTo(0.2f, tween(800))
  }

}

@Composable
private fun FrameTwo(
  textX: Animatable<Float, AnimationVector1D>,
  textY: Animatable<Float, AnimationVector1D>,
  ltadX: Animatable<Float, AnimationVector1D>,
  ltadY: Animatable<Float, AnimationVector1D>,
  madTextScale: Animatable<Float, AnimationVector1D>,
  makeMad: Boolean,
  makeMadAlpha: Animatable<Float, AnimationVector1D>,
  alphaColumn: Animatable<Float, AnimationVector1D>
) {
  Text(
    text = buildAnnotatedString {
      append(
        AnnotatedString(
          "M",
          spanStyle = SpanStyle(color = greenAndroidColor, fontWeight = FontWeight.Bold)
        )
      )
      append(
        AnnotatedString(
          "odern ",
          spanStyle = SpanStyle(color = blueColor, fontWeight = FontWeight.Bold)
        )
      )

      append(
        AnnotatedString(
          "A",
          spanStyle = SpanStyle(color = greenAndroidColor, fontWeight = FontWeight.Bold)
        )
      )
      append(
        AnnotatedString(
          "ndroid ",
          spanStyle = SpanStyle(color = blueColor, fontWeight = FontWeight.Bold)
        )
      )
      append(
        AnnotatedString(
          "D",
          spanStyle = SpanStyle(color = greenAndroidColor, fontWeight = FontWeight.Bold)
        )
      )
      append(
        AnnotatedString(
          "evelopment ",
          spanStyle = SpanStyle(color = blueColor, fontWeight = FontWeight.Bold)
        )
      )
    },
    style = Typography.h4.copy(fontFamily = FontFamily.Monospace),
    modifier = Modifier
      .offset { IntOffset(x = textX.value.toInt(), y = textY.value.toInt()) }
      .scale(madTextScale.value)
      .alpha(makeMadAlpha.value)
  )

  AnimatedVisibility(visible = makeMad) {
    Text(
      text = "MAD Skills",
      style = Typography.h4.copy(
        fontFamily = FontFamily.Monospace,
        color = greenAndroidColor,
        fontWeight = FontWeight.Bold
      ),
      modifier = Modifier
        .offset { IntOffset(x = textX.value.toInt(), y = textY.value.toInt()) }
        .scale(madTextScale.value)
    )
  }
  LTADColumn(ltadX.value, ltadY.value, alphaColumn.value)

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LTADColumn(ltadX: Float, ltadY: Float, alpha: Float) {
  Column(Modifier.offset { IntOffset(ltadX.toInt(), ltadY.toInt()) }) {
    ListItem(icon = {
      Icon(
        imageVector = Icons.Default.ShoppingCart,
        contentDescription = null,
        tint = greenAndroidColor,
        modifier = Modifier.size(36.dp)
      )
    }, text = {
      Text(
        text = "Language",
        style = Typography.h5.copy(fontWeight = FontWeight.Bold)
      )
    }, modifier = Modifier.alpha(alpha))
    Spacer(modifier = Modifier.height(8.dp))
    ListItem(icon = {
      Icon(
        imageVector = Icons.Default.Face, contentDescription = null, tint = blueColor,
        modifier = Modifier.size(36.dp)
      )
    }, text = {
      Text(
        text = "Tools", style = Typography.h5.copy(fontWeight = FontWeight.Bold),
      )
    }, modifier = Modifier.alpha(alpha))
    Spacer(modifier = Modifier.height(8.dp))
    ListItem(icon = {
      Icon(
        imageVector = Icons.Default.Home,
        contentDescription = null,
        tint = greenAndroidColor,
        modifier = Modifier.size(36.dp)
      )
    }, text = {
      Text(text = "APIs", style = Typography.h5.copy(fontWeight = FontWeight.Bold))
    })
    Spacer(modifier = Modifier.height(8.dp))
    ListItem(icon = {
      Icon(
        imageVector = Icons.Default.FavoriteBorder, contentDescription = null, tint = blueColor,
        modifier = Modifier.size(36.dp)
      )
    }, text = {
      Text(text = "Distribution", style = Typography.h5.copy(fontWeight = FontWeight.Bold))
    }, modifier = Modifier.alpha(alpha))
  }
}

@Composable
private fun FrameOne(
  firstOvalX: Animatable<Float, AnimationVector1D>,
  firstOvalY: Animatable<Float, AnimationVector1D>,
  cornerRadiusX: Animatable<Float, AnimationVector1D>,
  cornerRadiusY: Animatable<Float, AnimationVector1D>,
  ovalScaleX: Animatable<Float, AnimationVector1D>,
  ovalScaleY: Animatable<Float, AnimationVector1D>,
  secondOvalX: Animatable<Float, AnimationVector1D>,
  secondOvalY: Animatable<Float, AnimationVector1D>
) {
  Oval(
    firstOvalX.value,
    firstOvalY.value,
    cornerRadiusX.value,
    cornerRadiusY.value,
    greenAndroidColor,
    false,
    0f,
    ovalScaleX.value,
    ovalScaleY.value
  )
  Oval(
    secondOvalX.value,
    secondOvalY.value,
    cornerRadiusX.value,
    cornerRadiusY.value,
    blueColor,
    true,
    18f,
    ovalScaleX.value,
    ovalScaleY.value
  )
}

private suspend fun firstFrameFirstJob(
  widthPX: Float,
  ovalSizePx: Float,
  animationScope: CoroutineScope,
  firstOvalX: Animatable<Float, AnimationVector1D>,
  secondOvalX: Animatable<Float, AnimationVector1D>,
  ovalScaleX: Animatable<Float, AnimationVector1D>,
  ovalScaleY: Animatable<Float, AnimationVector1D>,
  cornerRadiusX: Animatable<Float, AnimationVector1D>,
  cornerRadiusY: Animatable<Float, AnimationVector1D>,
) {
  val firstOvalWhenCenter = (widthPX / 2).minus(ovalSizePx)
  val secondOvalWhenCenter = widthPX.minus((widthPX / 2)).plus(ovalSizePx.div(2))

  val firstFrameJob1 = animationScope.launch {
    // bring first oval to center
    firstOvalX.animateTo(
      firstOvalWhenCenter,
      tween(durationMillis = 1000, easing = FastOutLinearInEasing)
    )
  }
  val firstFrameJob2 = animationScope.launch {
    // bring first oval to center
    secondOvalX.animateTo(
      secondOvalWhenCenter,
      tween(durationMillis = 1000, easing = FastOutLinearInEasing)
    )
  }
  val firstFrameJob3 = animationScope.launch {
    // make these ovals as circle
    ovalScaleX.animateTo(ovalSizePx, tween(durationMillis = 1000, easing = FastOutLinearInEasing))
  }
  val firstFrameJob4 = animationScope.launch {
    // make these ovals as circle
    ovalScaleY.animateTo(ovalSizePx, tween(durationMillis = 1000, easing = FastOutLinearInEasing))
  }

  val cornerRadiusXJob = animationScope.launch {
    // make these ovals as circle
    cornerRadiusX.animateTo(400f, tween(durationMillis = 800, easing = FastOutLinearInEasing))
  }
  val cornerRadiusYJob = animationScope.launch {
    // make these ovals as circle
    cornerRadiusY.animateTo(400f, tween(durationMillis = 800, easing = FastOutLinearInEasing))
  }

  joinAll(
    firstFrameJob1,
    firstFrameJob2,
    firstFrameJob3,
    firstFrameJob4,
    cornerRadiusXJob,
    cornerRadiusYJob
  )
}

private suspend fun firstFrameSecondJob(
  animationScope: CoroutineScope,
  firstOvalX: Animatable<Float, AnimationVector1D>,
  ovalSizePx: Float,
  secondOvalX: Animatable<Float, AnimationVector1D>,
  widthPX: Float,
  ovalScaleX: Animatable<Float, AnimationVector1D>,
  ovalScaleY: Animatable<Float, AnimationVector1D>,
  cornerRadiusX: Animatable<Float, AnimationVector1D>,
  cornerRadiusY: Animatable<Float, AnimationVector1D>,
  aboutToFinish: () -> Unit
) {
  animationScope.launch {
    delay(800)
    aboutToFinish.invoke()
  }
  val secondFrameJob1 = animationScope.launch {
    // bring first oval to center
    firstOvalX.animateTo(
      0f.minus(ovalSizePx.times(2)),
      tween(durationMillis = 1000, easing = FastOutLinearInEasing)
    )
  }
  val secondFrameJob2 = animationScope.launch {
    // bring first oval to center
    secondOvalX.animateTo(
      widthPX.plus(ovalSizePx.times(2)),
      tween(durationMillis = 1000, easing = FastOutLinearInEasing)
    )
  }
  val secondFrameJob3 = animationScope.launch {
    // make these ovals as circle
    ovalScaleX.animateTo(
      ovalSizePx.times(2),
      tween(durationMillis = 800, easing = FastOutLinearInEasing)
    )
  }
  val secondFrameJob4 = animationScope.launch {
    // make these ovals as circle
    ovalScaleY.animateTo(ovalSizePx, tween(durationMillis = 800, easing = FastOutLinearInEasing))
  }

  val cornerRadiusXJob = animationScope.launch {
    // make these ovals as circle
    cornerRadiusX.animateTo(400f, tween(durationMillis = 800, easing = FastOutLinearInEasing))
  }
  val cornerRadiusYJob = animationScope.launch {
    // make these ovals as circle
    cornerRadiusY.animateTo(360f, tween(durationMillis = 800, easing = FastOutLinearInEasing))
  }

  joinAll(
    secondFrameJob1,
    secondFrameJob2,
    secondFrameJob3,
    secondFrameJob4,
    cornerRadiusXJob,
    cornerRadiusYJob
  )
}

@Composable
fun Oval(
  xValue: Float,
  yValue: Float,
  cornerRadiusX: Float,
  cornerRadiusY: Float,
  backgroundColor: Color,
  isBorder: Boolean,
  borderStroke: Float,
  scaleX: Float,
  scaleY: Float
) {

  val width = with(LocalDensity.current) {
    scaleX.toDp()
  }

  val height = with(LocalDensity.current) {
    scaleY.toDp()
  }


  Canvas(modifier = Modifier
    .width(width)
    .height(height)
    .offset { IntOffset(xValue.toInt(), yValue.toInt()) }, onDraw = {
    drawRoundRect(
      backgroundColor,
      cornerRadius = CornerRadius(cornerRadiusX, cornerRadiusY),
      style = if (isBorder) Stroke(borderStroke) else Fill,
    )
  })
}
