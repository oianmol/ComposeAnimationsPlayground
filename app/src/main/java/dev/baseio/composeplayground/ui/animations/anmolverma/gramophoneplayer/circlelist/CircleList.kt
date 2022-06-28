package dev.baseio.composeplayground.ui.animations.anmolverma.gramophoneplayer.circlelist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import dev.baseio.composeplayground.ui.theme.ComposePlaygroundTheme

@Composable
fun CircleList(
  visibleItems: Int,
  modifier: Modifier = Modifier,
  listState: ICircleListState = rememberCircleListState(),
  circularFraction: Float = 1f,
  direction: Direction = Direction.Right,
  content: @Composable () -> Unit,
) {
  check(visibleItems > 0) { "Visible items must be positive" }
  check(circularFraction > 0f) { "Circular fraction must be positive" }
  /**
   * Here measurables is the list of children that need to be measured and
   * constraints are the constraints from the parent.
   */
  Layout(
    modifier = modifier
        .clipToBounds()
        .onDrag(listState)
        .fillMaxSize(),
    content = content,
  ) { measurables, constraints ->

    // Don't constrain child views further, measure them with given constraints
    // List of measured children
    val placeables = getChildren(measurables, constraints, visibleItems)

    val config = CircleListConfig(
      contentHeight = constraints.maxHeight.toFloat(),
      contentWidth = constraints.maxWidth.toFloat(),
      numItems = placeables.size,
      visibleItems = visibleItems,
      circularFraction = circularFraction,
      direction = direction
    )

    // Setup state of the circleList
    listState.setup(config)

    // Set the size of the layout as big as it can
    layout(width = constraints.maxWidth, height = constraints.maxHeight) {
      for (i in listState.firstVisibleItem..listState.lastVisibleItem) {
        placeables[i].placeRelative(position = listState.getOffset(i), zIndex = 0f)
      }
    }
  }
}

/**
 * List of measured children
 * @param measurables is the list of children that need to be measured
 * @param constraints the constraints from the parent
 * @param visibleItems number of items visible
 */
fun getChildren(
  measurables: List<Measurable>,
  constraints: Constraints,
  visibleItems: Int
): List<Placeable> {
  val itemConstraints = Constraints.fixed(
    width = constraints.maxWidth,
    height = constraints.maxHeight / visibleItems
  )
  return measurables.map { measurable -> measurable.measure(itemConstraints) }
}

@Composable
fun rememberCircleListState(): ICircleListState {
  return rememberSaveable(saver = CircleListState.saver) {
    CircleListState()
  }
}

@Preview(showBackground = true, widthDp = 420)
@Composable
fun PreviewCircularList5() {
  ComposePlaygroundTheme {
    Surface {
      CircleList(
        visibleItems = 12,
        circularFraction = .65f,
        modifier = Modifier.fillMaxSize(),
      ) {
        for (i in 0 until 40) {
          Text(
            modifier = Modifier.wrapContentWidth(),
            text = "Item $i"
          )
        }
      }
    }
  }
}