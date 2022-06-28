package dev.baseio.composeplayground.ui.animations.anmolverma.gramophoneplayer.circlelist

import androidx.compose.ui.unit.IntOffset

interface ICircleListState {
  val verticalOffset: Float
  val firstVisibleItem: Int
  val lastVisibleItem: Int

  fun setup(config: CircleListConfig)
  suspend fun snapTo(value: Float)
  suspend fun decayTo(
    velocity: Float,
    value: Float
  )

  fun getOffset(index: Int): IntOffset
  suspend fun stop()
}