package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.ui.Modifier

actual fun Modifier.Companion.desktopModifier(
    enter: () -> Unit,
    exit: () -> Unit
): Modifier {
    return this
}