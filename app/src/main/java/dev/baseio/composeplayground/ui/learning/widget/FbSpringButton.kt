package dev.baseio.composeplayground.ui.learning.widget

import androidx.compose.animation.core.Spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import dev.baseio.composeplayground.ui.learning.interactions.SpringButtonTheme
import dev.baseio.composeplayground.ui.learning.interactions.SpringConfig
import dev.baseio.composeplayground.ui.learning.interactions.rememberSpringIndication

@Composable
fun FbSpringButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    springConfig: SpringConfig = SpringButtonTheme.defaultSpringConfig(),
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick,
        modifier.indication(interactionSource, rememberSpringIndication(springConfig)),
        enabled,
        interactionSource,
        elevation,
        shape,
        border,
        colors,
        contentPadding,
        content
    )
}

@Preview
@Composable
fun PReviewFbSpringButton() {
    MaterialTheme {
        Box(Modifier.fillMaxSize()) {
            FbSpringButton(
                onClick = {

                },
                modifier = Modifier.align(Alignment.Center),
                springConfig = SpringConfig(Spring.DampingRatioHighBouncy, Spring.StiffnessLow)
            ) {
                Text(text = "This text shows spring animation!")
            }
        }
    }
}