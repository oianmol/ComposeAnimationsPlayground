package dev.baseio.composeplayground.ui.animations.anmolverma.pulltomakesoup

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

object PullToMakeSoupYalantisAnimSpec {

  private const val ANIMATION_BUBBLE1_OFFSET = 200
  private const val ANIMATION_BUBBLE2_OFFSET = 300
  private const val ANIMATION_BUBBLE3_OFFSET = 500
  private const val ANIMATION_BUBBLE4_OFFSET = 700
  private const val ANIMATION_BUBBLE5_OFFSET = 800

  private const val ANIMATION_DURATION = 700
  private const val ANIMATION_SCALE_DURATION = 500
  private const val ANIMATION_FLAME_SCALE_DURATION = 100
  private const val ANIMATION_FLAME_BURN_DURATION = 180
  private const val ANIMATION_BUBBLE_DURATION = 980
  private const val ANIMATION_COVER_DURATION = 580
  private const val ANIMATION_COVER_OFFSET = 20

  @Composable
  fun flameBurn(infiniteTransition: InfiniteTransition): State<Float> {
    return infiniteTransition.animateFloat(
      initialValue = 0f, targetValue = 1f, animationSpec = InfiniteRepeatableSpec(
        animation = tween(
          ANIMATION_FLAME_BURN_DURATION
        ), repeatMode = RepeatMode.Reverse
      )
    )
  }


  /**
   * @param offset    With which offset should be started.
   *                  This method is responsible for configuring animation that animates the bubble move.
   *                  Animation can be started with some offset.
   * @return state
   */
  @Composable
  fun bubbleOffset(
    infiniteTransition: InfiniteTransition,
    startOffset: Float,
    endOffset: Float
  ): State<Float> {
    return infiniteTransition.animateFloat(
      initialValue = startOffset, targetValue = endOffset, animationSpec = InfiniteRepeatableSpec(
        animation = tween(
          ANIMATION_BUBBLE_DURATION
        ), repeatMode = RepeatMode.Restart
      )
    )
  }

  /**
   * @param animation
   * This method is responsible for configuring animation that animates the cover droping.
   * @return state
   */
  @Composable
  fun coverAnimation(
    infiniteTransition: InfiniteTransition,
  ): State<Float> {
    return infiniteTransition.animateFloat(
      initialValue = 0f, targetValue = 1f, animationSpec = InfiniteRepeatableSpec(
        animation = tween(
          ANIMATION_COVER_DURATION
        ), repeatMode = RepeatMode.Reverse
      )
    )
  }

  /**
   * @param animation
   * This method is responsible for configuring animation that animates the flame appearance.
   * @return state
   */
  @Composable
  fun flameScale(
    infiniteTransition: InfiniteTransition,
  ): State<Float> {
    return infiniteTransition.animateFloat(
      initialValue = 0f, targetValue = 1f, animationSpec = InfiniteRepeatableSpec(
        animation = tween(
          ANIMATION_FLAME_SCALE_DURATION
        ), repeatMode = RepeatMode.Reverse
      )
    )
  }


  /**
   * @param animation
   * This method is responsible for configuring animation that animates scaling of water.
   * @return state
   */
  @Composable
  fun waterScale(
    infiniteTransition: InfiniteTransition,
  ): State<Float> {
    return infiniteTransition.animateFloat(
      initialValue = 0f, targetValue = 1f, animationSpec = InfiniteRepeatableSpec(
        animation = tween(
          ANIMATION_SCALE_DURATION
        ), repeatMode = RepeatMode.Reverse
      )
    )
  }

  /**
   * @param animation
   * This method is responsible for configuring animation that animates the bounce,when the vegetables are dropped.
   * @return state
   */
  @Composable
  fun bounceVegetables(
    infiniteTransition: InfiniteTransition,
  ): State<Float> {
    return infiniteTransition.animateFloat(
      initialValue = 0f, targetValue = 1f, animationSpec = InfiniteRepeatableSpec(
        animation = tween(
          ANIMATION_DURATION
        ), repeatMode = RepeatMode.Reverse
      )
    )
  }

}