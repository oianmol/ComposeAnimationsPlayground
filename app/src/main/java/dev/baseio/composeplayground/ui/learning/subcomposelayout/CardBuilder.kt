package dev.baseio.composeplayground.ui.learning.subcomposelayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy

@Composable
@UiComposable
fun CardBuilder(
    image: @Composable @UiComposable () -> Unit,
    title: @Composable @UiComposable () -> Unit,
    subtitle: @Composable @UiComposable () -> Unit,
) {
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        layout(layoutWidth, layoutHeight) {
            val imagePlaceable = subcompose(CardBuilderLayoutContent.Image, image).fastMap {
                it.measure(looseConstraints)
            }
            val imageHeight = imagePlaceable.fastMaxBy { it.height }?.height ?: 0

            val titlePlaceable = subcompose(CardBuilderLayoutContent.Title, title).fastMap {
                it.measure(looseConstraints)
            }
            val titleHeight = titlePlaceable.fastMaxBy { it.height }?.height ?: 0

            val subTitlePlaceable =
                subcompose(CardBuilderLayoutContent.SubTitle, subtitle).fastMap {
                    it.measure(looseConstraints)
                }
            val subTitleHeight = subTitlePlaceable.fastMaxBy { it.height }?.height ?: 0
            imagePlaceable.fastForEach {
                it.place(0, 0)
            }
            titlePlaceable.fastForEach {
                it.place(0, imageHeight)
            }
            subTitlePlaceable.fastForEach {
                it.place(0, imageHeight + titleHeight)
            }

        }
    }
}

private enum class CardBuilderLayoutContent { Image, Title, SubTitle }


@Preview
@Composable
fun PreviewCardBuilder() {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardBuilder(image = {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(120.dp)
                        .background(Color.Yellow)
                )
            }, title = {
                Text(text = "Title")
            }, subtitle = {
                Text(text = "SubTitle")
            })
        }
    }
}