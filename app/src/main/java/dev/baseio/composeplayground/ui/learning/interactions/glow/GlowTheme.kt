package dev.baseio.composeplayground.ui.learning.interactions.glow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

interface GlowingTheme {
    @Composable
    fun glowConfiguration(): GlowConfig

    companion object {
        fun defaultGlowConfig() = GlowConfig(Color(0xffFFBF00), 8.0f)
    }
}

@Immutable
class GlowConfig(
    val glowColor: Color,
    val blurRadius: Float
)

@Immutable
private object DebugGlowTheme : GlowingTheme {
    @Composable
    override fun glowConfiguration(): GlowConfig = GlowingTheme.defaultGlowConfig()

}

val LocalGlowTheme: ProvidableCompositionLocal<GlowingTheme> =
    staticCompositionLocalOf { DebugGlowTheme }