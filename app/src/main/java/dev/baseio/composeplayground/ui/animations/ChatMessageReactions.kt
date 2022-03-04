package dev.baseio.composeplayground.ui.animations

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.ui.theme.Typography

/**
 * https://github.com/GetStream/swiftui-spring-animations
 * https://github.com/GetStream/swiftui-spring-animations/blob/main/SwiftUISpringAnimations/ReactionsViewInterpolatingSpringSD.swift
 */
@Composable
fun ChatMessageReactions(modifier: Modifier) {
  var isShowReaction by remember {
    mutableStateOf(false)
  }
  Surface(
    modifier
      .background(MaterialTheme.colors.onBackground)
  ) {

    val scaleEffect by animateFloatAsState(
      targetValue = if (isShowReaction) 1f else 0f,
      animationSpec = spring(0.35f, stiffness = 270f),
    )

    val rotationEffect by animateFloatAsState(
      targetValue = if (isShowReaction) 0f else -45f,
      animationSpec = spring(0.35f, stiffness = 270f)
    )

    val textOffset by animateDpAsState(
      targetValue = if (isShowReaction) 0.dp else (-15).dp,
      animationSpec = spring(0.35f, stiffness = 270f),
    )

    val textOffset2 by animateDpAsState(
      targetValue = if (isShowReaction) 0.dp else (15).dp,
      animationSpec = spring(0.35f, stiffness = 270f),
    )

    LaunchedEffect(true) {
      isShowReaction = !isShowReaction

    }

    Column(horizontalAlignment = Alignment.CenterHorizontally){
      Button(onClick = {
        isShowReaction = !isShowReaction
      }) {
        Text(text = "Repeat Animation!")
      }
      Spacer(modifier = Modifier.height(28.dp))
      Column(
        Modifier
          .padding(4.dp)
          .graphicsLayer(
            scaleX = scaleEffect,
            scaleY = scaleEffect,
            transformOrigin = topTrailing()
          )
          .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(28.dp))
      ) {
        Row(
          Modifier.padding(8.dp)
        ) {
          Text(
            "❤️",
            Modifier
              .graphicsLayer(
                scaleX = scaleEffect, scaleY = scaleEffect,
                transformOrigin = bottomLeading(), rotationZ = rotationEffect
              )
              .offset(x = textOffset),
            style = Typography.h4
          )
          Text(
            "\uD83D\uDC4D",
            Modifier
              .graphicsLayer(
                scaleX = scaleEffect,
                scaleY = scaleEffect,
                transformOrigin = bottom()
              )
              .offset(x = textOffset),
            style = Typography.h4
          )
          Text(
            "\uD83D\uDC4E",
            Modifier
              .graphicsLayer(
                scaleX = scaleEffect,
                scaleY = scaleEffect,
                transformOrigin = topTrailing()
              )
              .rotate(rotationEffect)
              .offset(x = textOffset),
            style = Typography.h4
          )
          Text(
            "\uD83D\uDE2D",
            Modifier
              .graphicsLayer(
                scaleX = scaleEffect,
                scaleY = scaleEffect,
                transformOrigin = bottom()
              )
              .rotate(rotationEffect),
            style = Typography.h4
          )
          Text(
            "\uD83D\uDE02",
            Modifier
              .graphicsLayer(
                scaleX = scaleEffect,
                scaleY = scaleEffect,
                transformOrigin = bottomTrailing()
              )
              .offset(x = textOffset2),
            style = Typography.h4
          )
        }
      }
    }
  }
}

@Composable
private fun bottom() = TransformOrigin(0.5f, 1f)

@Composable
private fun bottomTrailing() = TransformOrigin(1f, 1f)

@Composable
private fun bottomLeading() = TransformOrigin(0.5f, 1f)

@Composable
private fun topTrailing() = TransformOrigin(1f, 0f)