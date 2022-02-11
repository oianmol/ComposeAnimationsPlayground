package dev.baseio.composeplayground.ui.menutoclose

import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp


/**
 * https://twitter.com/amos_gyamfi/status/1491506689408909316
 */
@Composable
fun MenuToClose(modifier: Modifier) {
  Surface(modifier.background(MaterialTheme.colors.background)) {

    var isRotated by remember {
      mutableStateOf(false)
    }
    var isHidden by remember {
      mutableStateOf(false)
    }

    val rotateFirst by animateFloatAsState(
      targetValue = if (isRotated) 48f else 0f,
      animationSpec = springSpec(),
    )
    val rotateSecond by animateFloatAsState(
      targetValue = if (isRotated) -48f else 0f,
      animationSpec = springSpec(),
    )
    val hiddenFirst by animateFloatAsState(
      targetValue = if (isHidden) 0f else 1f,
      animationSpec = springSpec(),
    )

    Column(
      Modifier
        .padding(32.dp)
        .height(if(isHidden) 40.dp else 50.dp)
        .clickable {
          isRotated = !isRotated
          isHidden = !isHidden
        }, verticalArrangement = if (isHidden) Arrangement.SpaceAround else Arrangement.SpaceBetween
    ) {

      Box(
        Modifier
          .graphicsLayer(
            shape = RoundedCornerShape(8.dp),
            rotationZ = rotateFirst,
            transformOrigin = TransformOrigin(0.3f, 0.3f)
          )
          .width(64.dp)
          .background(MaterialTheme.colors.onBackground)
          .height(10.dp)

      )

      if (!isHidden) {
        Box(
          Modifier
            .graphicsLayer(
              shape = RoundedCornerShape(8.dp),
              scaleX = hiddenFirst, scaleY = hiddenFirst, alpha = hiddenFirst
            )
            .width(64.dp)
            .height(10.dp)
            .background(MaterialTheme.colors.onBackground)

        )
      }


      Box(
        Modifier
          .graphicsLayer(
            shape = RoundedCornerShape(8.dp),
            rotationZ = rotateSecond,
            transformOrigin = TransformOrigin(0.3f, 0.3f)
          )
          .width(64.dp)
          .height(10.dp)
          .background(MaterialTheme.colors.onBackground)

      )

    }
  }
}

@Composable
private fun springSpec(): SpringSpec<Float> =
  spring(0.35f, stiffness = 300f)
