package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.nativeCanvas
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.PaintMode
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.PaintStrokeJoin
import org.jetbrains.skia.Font
import org.jetbrains.skia.TextLine
import org.jetbrains.skia.Typeface

actual fun Canvas.drawText(
    it: String,
    x: Float,
    y: Float,
    paint: NativePaint,
    textSize: Float,
    style: PaintMode,
    strokeJoin: PaintStrokeJoin?
) {
    nativeCanvas.drawTextLine(
        TextLine.Companion.make(it, Font(Typeface.makeDefault(), textSize)),
        x,
        y, paint.apply {
            this.mode = org.jetbrains.skia.PaintMode.valueOf(style.name)
            strokeJoin?.let {
                this@apply.strokeJoin = org.jetbrains.skia.PaintStrokeJoin.valueOf(it.name)
            }
        }
    )
}