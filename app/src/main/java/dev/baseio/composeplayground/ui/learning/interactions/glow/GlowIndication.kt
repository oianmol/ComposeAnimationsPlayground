package dev.baseio.composeplayground.ui.learning.interactions.glow

import androidx.compose.animation.core.*
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.*

class GlowIndication(private val glowConfig: State<GlowConfig>? = null) : Indication {

    class GlowIndicationInstance(
        private val glow: State<GlowConfig>,
        private val animationState: State<Float>,
        private val isFocused: State<Boolean>,
        private val isHovered: State<Boolean>,
        private val isPressed: State<Boolean>,
    ) : IndicationInstance {

        override fun ContentDrawScope.drawIndication() {
            this@drawIndication.drawContent()
            if (isHovered.value || isFocused.value) {
                drawRoundRect(
                    glow.value.glowColor,
                    alpha = animationState.value,
                    style = Stroke(
                        width = 4f,
                        cap = StrokeCap.Round
                    ), cornerRadius = CornerRadius(8f, 8f)
                )
            }else{
                drawRoundRect(
                    glow.value.glowColor,
                    alpha = 1f,
                    style = Stroke(
                        width = 4f,
                        cap = StrokeCap.Round
                    ), cornerRadius = CornerRadius(8f, 8f)
                )
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val theme = LocalGlowTheme.current
        val animated = rememberInfiniteTransition()
        val animationState = animated.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(500),
                repeatMode = RepeatMode.Reverse
            )
        )
        val glow = rememberUpdatedState(
            glowConfig?.value ?: theme.glowConfiguration()
        )
        val isPressed = interactionSource.collectIsPressedAsState()
        val isHovered = interactionSource.collectIsHoveredAsState()
        val isFocused = interactionSource.collectIsFocusedAsState()
        return remember {
            GlowIndicationInstance(glow, animationState, isFocused, isHovered, isPressed)
        }
    }


}

@Composable
fun rememberGlowIndication(
    glowConfig: GlowConfig
): Indication {
    val glowConfigState = rememberUpdatedState(glowConfig)
    return remember {
        GlowIndication(glowConfigState)
    }
}
