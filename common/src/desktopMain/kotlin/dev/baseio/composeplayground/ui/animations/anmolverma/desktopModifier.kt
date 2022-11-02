package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.Companion.desktopModifier(enter: () -> Unit, exit: () -> Unit): Modifier {
    return this.then(onPointerEvent(PointerEventType.Enter) {
        enter()
    }.then(onPointerEvent(PointerEventType.Exit) {
        exit()
    }))
}