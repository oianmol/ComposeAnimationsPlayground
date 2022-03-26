package dev.baseio.composeplayground.ui.animations.anmolverma.netflixanim

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.ui.animations.baseColor


@Composable
fun EffectBrush(
  brushMoving: Animatable<Float, AnimationVector1D>,
  modifier: Modifier,
  nWidth: Dp
) {

  val brushList by remember {
    mutableStateOf(brushFurList.reversed())
  }

  val height = LocalDensity.current.run { LocalConfiguration.current.screenHeightDp.dp.toPx() }

  Box(
    modifier = modifier
      .graphicsLayer(translationY = brushMoving.value)
  ) {
    repeat(brushList.size) {
      val brushFur = brushList[it]
      val xOffset =
        (brushFur.left / 100).times(LocalDensity.current.run { nWidth.toPx().times(0.19f) })
      Box(
        modifier = Modifier
          .width(LocalDensity.current.run { brushFur.width.toDp() })
          .offset { IntOffset(xOffset.toInt(), 0) }
          .fillMaxHeight()
          .graphicsLayer(translationY = brushMoving.value.times(height.div(100)))
          .background(brushFur.background)
      )
    }

  }
}


val brushFurList: List<BrushFurModel> = mutableListOf<BrushFurModel>().apply {

  add(
    BrushFurModel(
      left = 0f, width = 3.8f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(15f / 100, baseColor),
        Pair(81f / 100, Color(0, 0, 0, 0)),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 3.8f, width = 2.8f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(10f / 100, baseColor),
        Pair(62f / 100, Color(0, 0, 0, 0)),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 6.6f, width = 4.8f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(37f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 11.4f, width = 4f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(23f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 15.4f, width = 4f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(15f / 100, baseColor),
        Pair(86f / 100, Color(0, 0, 0, 0)),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 19.4f, width = 2.5f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(27f / 100, baseColor),
        Pair(89f / 100, Color(0, 0, 0, 0)),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 21.9f, width = 4f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(20f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 25.9f, width = 2f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(30f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 27.9f, width = 4f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(35f / 100, baseColor),
        Pair(95f / 100, Color(0, 0, 0, 0)),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 31.9f, width = 3.5f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(39f / 100, baseColor),
        Pair(95f / 100, Color(0, 0, 0, 0)),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 35.4f, width = 2f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(34f / 100, baseColor),
        Pair(95f / 100, Color(0, 0, 0, 0)),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 37.4f, width = 2.6f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(22f / 100, baseColor),
        Pair(95f / 100, Color(0, 0, 0, 0)),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 40f, width = 6f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(47f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 46f, width = 2f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(36f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 48f, width = 5.5f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(29f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 53.5f, width = 3f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(39f / 100, baseColor),
        Pair(95f / 100, Color(0, 0, 0, 0)),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 56.5f, width = 4.1f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(45f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 60.6f, width = 2.4f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(34f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 63f, width = 4f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(47f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )

  add(
    BrushFurModel(
      left = 67f, width = 1.5f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(27f / 100, baseColor),
        Pair(95f / 100, Color(0, 0, 0, 0)),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 68.5f, width = 2.8f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(37f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 71.3f, width = 2.3f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(9f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 73.6f, width = 2.2f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(28f / 100, baseColor),
        Pair(92f / 100, Color(0, 0, 0, 0)),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 75.8f, width = 1f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(37f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 76.8f, width = 2.1f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(28f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 78.9f, width = 4.1f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(21f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 83f, width = 2.5f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(21f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 85.5f, width = 4.5f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(39f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 90f, width = 2.8f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(30f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 92.8f, width = 3.5f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(19f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
  add(
    BrushFurModel(
      left = 96.3f, width = 3.7f,
      background = Brush.linearGradient(
        Pair(0f / 100, baseColor),
        Pair(37f / 100, baseColor),
        Pair(100f / 100, Color(0, 0, 0, 0))
      )
    )
  )
}