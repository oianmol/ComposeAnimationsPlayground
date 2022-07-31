package dev.baseio.composeplayground.ui.learning.widget

import androidx.compose.foundation.Indication
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.baseio.composeplayground.ui.learning.interactions.glow.GlowConfig
import dev.baseio.composeplayground.ui.learning.interactions.glow.GlowIndication
import dev.baseio.composeplayground.ui.learning.interactions.glow.GlowingTheme

@Composable
fun GlowingTextField(modifier: Modifier) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var glowText by remember {
        mutableStateOf("")
    }
    TextField(
        value = glowText,
        onValueChange = { s ->
            glowText = s
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier.indication(
            interactionSource,
            rememberGlowIndicator(GlowingTheme.defaultGlowConfig())
        )
    )
}


@Composable
fun rememberGlowIndicator(
    glowConfig: GlowConfig
): Indication {
    val glowConfigState = rememberUpdatedState(glowConfig)
    return remember {
        GlowIndication(glowConfigState)
    }
}


@Preview
@Composable
fun PReviewGlowingTextField() {
    MaterialTheme {
        Box(Modifier.fillMaxSize()) {
            GlowingTextField(modifier = Modifier.align(Alignment.Center))
        }
    }
}