package dev.baseio.composeplayground.ui.learning.interactions

import androidx.compose.animation.core.Spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

interface SpringButtonTheme {
    @Composable
    fun springConfiguration(): SpringConfig

    companion object {
        fun defaultSpringConfig() = SpringConfig()
    }
}

@Immutable
class SpringConfig(
    val dampingRatio: Float = Spring.DampingRatioNoBouncy,
    val stiffness: Float = Spring.StiffnessMedium,
    val visibilityThreshold: Float? = null
)

@Immutable
private object DebugSpringTheme : SpringButtonTheme {
    @Composable
    override fun springConfiguration(): SpringConfig = SpringButtonTheme.defaultSpringConfig()

}

public val LocalSpringTheme: ProvidableCompositionLocal<SpringButtonTheme> =
    staticCompositionLocalOf { DebugSpringTheme }