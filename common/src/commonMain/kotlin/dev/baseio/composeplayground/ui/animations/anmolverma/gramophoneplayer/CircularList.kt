package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FloatSpringSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.verticalDrag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sqrt


const val totalItemsOnScreen = 5

/**
 * Reference from https://fvilarino.medium.com/creating-a-circular-list-in-jetpack-compose-29924c70e3e0
 */
data class CircularListConfig(
  val fullDiscHeight: Float = 0f,
  val circularFraction: Float = 1f,
)

@Stable
interface CircularListState {
  val verticalOffset: Float
  val firstVisibleItem: Int
  val lastVisibleItem: Int

  suspend fun snapTo(value: Float)
  suspend fun decayTo(velocity: Float, value: Float)
  suspend fun stop()
  fun offsetFor(index: Int): IntOffset
  fun setup(config: CircularListConfig)
}

class CircularListStateImpl(
  currentOffset: Float = 0f,
) : CircularListState {

  private val animatable = Animatable(currentOffset)
  private var albumHeight = 0f
  private var config = CircularListConfig()
  private var initialOffset = 0f
  private val decayAnimationSpec = FloatSpringSpec(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessLow,
  )

  private val minOffset: Float
    get() = -(totalItemsOnScreen - 1) * albumHeight

  override val verticalOffset: Float
    get() = animatable.value

  override val firstVisibleItem: Int
    get() = ((-verticalOffset - initialOffset) / albumHeight).toInt().coerceAtLeast(0)

  override val lastVisibleItem: Int
    get() = (((-verticalOffset - initialOffset) / albumHeight).toInt() + totalItemsOnScreen)
      .coerceAtMost(totalItemsOnScreen - 1)

  override suspend fun snapTo(value: Float) {
    val minOvershoot = -(totalItemsOnScreen - 1) * albumHeight
    val maxOvershoot =  albumHeight
    animatable.snapTo(value.coerceIn(minOvershoot, maxOvershoot))
  }

  override suspend fun decayTo(velocity: Float, value: Float) {
    val constrainedValue = value.coerceIn(minOffset, 0f).absoluteValue
    val remainder = (constrainedValue / albumHeight) - (constrainedValue / albumHeight).toInt()
    val extra = if (remainder <= 0.5f) 0 else 1
    val target = ((constrainedValue / albumHeight).toInt() + extra) * albumHeight
    animatable.animateTo(
      targetValue = -target,
      initialVelocity = velocity,
      animationSpec = decayAnimationSpec,
    )
  }

  override suspend fun stop() {
    animatable.stop()
  }

  override fun setup(config: CircularListConfig) {
    this.config = config
    albumHeight = (config.fullDiscHeight / totalItemsOnScreen)
    initialOffset = (config.fullDiscHeight - albumHeight) / 2f
  }

  override fun offsetFor(index: Int): IntOffset {
    val maxOffset = config.fullDiscHeight / 2f + albumHeight / 2f
    val y = (verticalOffset + initialOffset + index * albumHeight)
    val deltaFromCenter = (y - initialOffset)
    val radius = config.fullDiscHeight / 2f
    val scaledY = deltaFromCenter.absoluteValue * 
        (config.fullDiscHeight.div(2f).div(maxOffset))
    val x = if (scaledY < radius) {
      sqrt((radius * radius - scaledY * scaledY))
    } else {
      0f
    }
    return IntOffset(
      x = (x * config.circularFraction).roundToInt(),
      y = y.roundToInt()
    )
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    other as CircularListStateImpl

    if (animatable.value != other.animatable.value) return false
    if (albumHeight != other.albumHeight) return false
    if (config != other.config) return false
    if (initialOffset != other.initialOffset) return false
    if (decayAnimationSpec != other.decayAnimationSpec) return false

    return true
  }

  override fun hashCode(): Int {
    var result = animatable.value.hashCode()
    result = 31 * result + albumHeight.hashCode()
    result = 31 * result + config.hashCode()
    result = 31 * result + initialOffset.hashCode()
    result = 31 * result + decayAnimationSpec.hashCode()
    return result
  }

  companion object {
    val Saver = Saver<CircularListStateImpl, List<Any>>(
      save = { listOf(it.verticalOffset) },
      restore = {
        CircularListStateImpl(it[0] as Float)
      }
    )
  }
}

@Composable
fun rememberCircularListState(): CircularListState {
  val state = rememberSaveable(saver = CircularListStateImpl.Saver) {
    CircularListStateImpl()
  }
  return state
}

@Composable
fun CircularList(
  modifier: Modifier = Modifier,
  state: CircularListState = rememberCircularListState(),
  circularFraction: Float = -1f,
  content: @Composable () -> Unit,
) {
  Layout(
    modifier = modifier
      .clipToBounds()
      .drag(state),
    content = content,
  ) { measurables, constraints ->
    val consMaxHeight = constraints.maxHeight
    val conMaxWidth = constraints.maxWidth

    val itemHeight = (consMaxHeight / totalItemsOnScreen)

    val itemConstraints = Constraints.fixed(width = conMaxWidth, height = itemHeight)
    val placeables = measurables.map { measurable -> measurable.measure(itemConstraints) }
    state.setup(
      CircularListConfig(
        fullDiscHeight = consMaxHeight.toFloat(),
        circularFraction = circularFraction,
      )
    )
    layout(
      width = conMaxWidth,
      height = consMaxHeight,
    ) {
      for (i in state.firstVisibleItem..state.lastVisibleItem) {
        placeables[i].placeRelative(state.offsetFor(i))
      }
    }
  }
}

private fun Modifier.drag(
  state: CircularListState,
) = pointerInput(Unit) {
  val decay = splineBasedDecay<Float>(this)
  coroutineScope {
    while (true) {
      val pointerId = awaitPointerEventScope { awaitFirstDown().id }
      state.stop()
      val tracker = VelocityTracker()
      awaitPointerEventScope {
        verticalDrag(pointerId) { change ->
          val verticalDragOffset = state.verticalOffset + change.positionChange().y
          launch {
            state.snapTo(verticalDragOffset)
          }
          tracker.addPosition(change.uptimeMillis, change.position)
          change.consumePositionChange()
        }
      }
      val velocity = tracker.calculateVelocity().y
      val targetValue = decay.calculateTargetValue(state.verticalOffset, velocity)
      launch {
        state.decayTo(velocity, targetValue)
      }
    }
  }
}
