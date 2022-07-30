package dev.baseio.composeplayground.ui.learning.interactions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SpringIndication(private val springConfig: State<SpringConfig>? = null) : Indication {
    private val animatedSpring = Animatable(1f)

    class SpringIndicationInstance(
        private val isPressed: State<Boolean>,
        private val scope: CoroutineScope,
        private val spring: State<SpringConfig>,
        private val animatedSpring: Animatable<Float, AnimationVector1D>
    ) : IndicationInstance {

        override fun ContentDrawScope.drawIndication() {
            scale(animatedSpring.value, center) {
                this@drawIndication.drawContent()
            }

            if (isPressed.value) {
                scope.launch {
                    animatedSpring.animateTo(
                        1.3f, animationSpec = spring(
                            spring.value.dampingRatio,
                            spring.value.stiffness,
                            spring.value.visibilityThreshold
                        )
                    )
                }
            } else {
                scope.launch {
                    animatedSpring.animateTo(
                        1f, animationSpec = spring(
                            spring.value.dampingRatio,
                            spring.value.stiffness,
                            spring.value.visibilityThreshold
                        )
                    )
                }
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val theme = LocalSpringTheme.current
        val scope = rememberCoroutineScope()
        val spring = rememberUpdatedState(
            springConfig?.value ?: theme.springConfiguration()
        )
        val isPressed = interactionSource.collectIsPressedAsState()
        return remember(interactionSource) {
            SpringIndicationInstance(isPressed, scope, spring, animatedSpring)
        }
    }


}

@Composable
fun rememberSpringIndication(
    springConfig: SpringConfig
): Indication {
    val springConfigState = rememberUpdatedState(springConfig)
    return remember {
        SpringIndication(springConfigState)
    }
}



