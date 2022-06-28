package dev.baseio.composeplayground.ui.animations.anmolverma.gramophoneplayer.circlelist

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FloatSpringSpec
import androidx.compose.animation.core.Spring
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.unit.IntOffset
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sqrt

class CircleListState(
  currentOffset: Float = 0f,
) : ICircleListState {

  private val animatable = Animatable(currentOffset)
  private var itemHeight = 0f
  private var config = CircleListConfig()
  private var initialOffsetY = 0f

  private val decayAnimationSpec = FloatSpringSpec(
    dampingRatio = Spring.DampingRatioLowBouncy,
    stiffness = Spring.StiffnessLow,
    visibilityThreshold = Spring.DefaultDisplacementThreshold
  )

  private val minOffset: Float
    get() = -(config.numItems - 1) * itemHeight

  override val verticalOffset: Float
    get() = animatable.value

  override val firstVisibleItem: Int
    get() = ((-verticalOffset - initialOffsetY) / itemHeight).toInt()
      .coerceAtLeast(0)

  override val lastVisibleItem: Int
    get() = (((-verticalOffset - initialOffsetY) / itemHeight).toInt() + config.visibleItems)
      .coerceAtMost(config.numItems - 1)

  override fun setup(config: CircleListConfig) {
    this.config = config
    itemHeight = config.contentHeight / config.visibleItems
    initialOffsetY = config.contentHeight / 2f
  }

  override suspend fun snapTo(value: Float) {
    val minOvershoot = -(config.numItems - 1) * itemHeight
    val maxOvershoot = itemHeight
    animatable.snapTo(value.coerceIn(minOvershoot, maxOvershoot))
  }

  override suspend fun decayTo(
    velocity: Float,
    value: Float
  ) {
    val constrainedValue = value.coerceIn(minOffset, 0f).absoluteValue
    val remainder = (constrainedValue / itemHeight) - (constrainedValue / itemHeight).toInt()
    val extra = if (remainder <= 0.5f) 0 else 1
    val target = ((constrainedValue / itemHeight).toInt() + extra) * itemHeight
    animatable.animateTo(
      targetValue = -target,
      initialVelocity = velocity,
      animationSpec = decayAnimationSpec,
    )
  }

  override fun getOffset(index: Int): IntOffset {

    // Calculate how much an item may scroll vertically, this will determine when the item will be at the 0 horizontal coordinate
    val maxOffset = (config.contentHeight + itemHeight) / 2f

    // Calculate the vertical y-position based on scroll
    val y = verticalOffset + initialOffsetY + (index * itemHeight) + 85

    // Calculate how far the current item is from the center of the screen, vertically
    val deltaFromCenter = y - initialOffsetY + 200

    // Define the radius of our imaginary circle, the circle is as tall as our container, so the radius is half that
    val radius = config.contentHeight / 2f

    // The vertical delta we calculated earlier needs to be adjusted, the delta is constrained to half the container height,
    // but items can scroll a bit further as they are rendered as they scroll off the screen, so here we adjust the delta accordingly
    val scaledY = (deltaFromCenter.absoluteValue * config.contentHeight) / (2f * maxOffset) + 550

    // Calculate the offset on the horizontal axis based on the radius and the vertical offset
    // Pythagoras theorem: https://www.veritasprep.com/blog/2016/10/how-to-use-the-pythagorean-theorem-with-a-circle
    val x = if (scaledY < radius) {
      sqrt((radius * radius - scaledY * scaledY))
    } else {
      0f
    }

    // Apply the circular fraction
    val circularX = x * config.circularFraction

    // Decide the offsetX based on circle direction
    val offsetX = (if (config.direction == Direction.Right) {
      config.contentWidth - circularX
    } else {
      circularX
    }).roundToInt()

    val offsetY = y.roundToInt()

    return IntOffset(
      x = offsetX,
      y = offsetY
    )
  }

  override suspend fun stop() {
    animatable.stop()
  }

  companion object {
    // Custom saver object to use in rememberSaveable
    val saver: Saver<CircleListState, List<Any>> by lazy {
      Saver(save = { listOf(it.verticalOffset) }, restore = { CircleListState(it[0] as Float) })
    }
  }
}