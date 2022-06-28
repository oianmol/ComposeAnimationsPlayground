package dev.baseio.composeplayground.ui.animations.anmolverma.gramophoneplayer.circlelist

data class CircleListConfig(
  val contentHeight: Float = 0f,
  val contentWidth: Float = 0f,
  val numItems: Int = 0,
  val visibleItems: Int = 0,
  val circularFraction: Float = 1f,
  val direction: Direction = Direction.Right
)

sealed class Direction {
  object Right : Direction()
  object Left : Direction()
}