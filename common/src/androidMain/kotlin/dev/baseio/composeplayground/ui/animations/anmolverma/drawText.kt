package dev.baseio.composeplayground.ui.animations.anmolverma

import android.graphics.Paint.Join
import android.graphics.Paint.Style
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.nativeCanvas
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.PaintMode
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.PaintStrokeJoin

actual fun Canvas.drawText(
    it: String,
    x: Float,
    y: Float,
    paint: NativePaint,
    textSize: Float,
    style: PaintMode,
    strokeJoin: PaintStrokeJoin?
) {
    nativeCanvas.drawText(
        it,
        x,
        y, paint.apply {
            this.textSize = 32f
            this@apply.style = Style.valueOf(style.name)
            strokeJoin?.let {
                this@apply.strokeJoin = Join.valueOf(it.name)
            }
        }
    )
}