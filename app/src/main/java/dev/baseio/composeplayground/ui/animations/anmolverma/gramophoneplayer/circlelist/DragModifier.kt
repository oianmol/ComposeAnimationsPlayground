package dev.baseio.composeplayground.ui.animations.anmolverma.gramophoneplayer.circlelist

import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.verticalDrag
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onDrag(
  listState: ICircleListState,
) = pointerInput(Unit) {

  // Used to calculate a settling position of a fling animation
  val decay = splineBasedDecay<Float>(this)

  // Wrap in a coroutine scope to use suspend functions for touch events and animation
  coroutineScope {
    while (true) {
      // awaitPointerEventScope suspends and installs a pointer
      // that can await events and react to them immediately.
      // Wait for a touch down event
      val pointerId = awaitPointerEventScope { awaitFirstDown().id }

      // Interrupt any ongoing animation
      listState.stop()

      // Prepare for drag events and record velocity of a fling
      val tracker = VelocityTracker()

      // Wait for drag events
      awaitPointerEventScope {
        verticalDrag(pointerId) { change ->
          // Record the position after offset
          val verticalDragOffset = listState.verticalOffset + change.positionChange().y
          launch {
            // Overwrite the Animatable value while the element is dragged
            listState.snapTo(verticalDragOffset)
          }
          // Record the velocity of the drag
          tracker.addPosition(change.uptimeMillis, change.position)

          // Consume the gesture event, not passed to external
          if (change.positionChange() != Offset.Zero) {
            change.consume()
          }
        }
      }

      // Dragging finished. Calculate the velocity of the fling
      val velocity = tracker.calculateVelocity().y

      // Calculate where the element eventually settles after the fling animation
      val targetValue = decay.calculateTargetValue(listState.verticalOffset, velocity)

      launch {
        // Enough velocity to slide away the element
        listState.decayTo(velocity, targetValue)
      }
    }
  }
}