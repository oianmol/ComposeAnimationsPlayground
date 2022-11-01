package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.NativePaint
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.PaintMode
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.PaintStrokeJoin

expect fun Canvas.drawText(
    it: String,
    x: Float,
    y: Float,
    paint: NativePaint,
    textSize: Float,
    style: PaintMode,
    strokeJoin: PaintStrokeJoin? = null
)