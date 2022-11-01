package dev.baseio.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp

data class WindowInfo(val screenWidthDp: Dp, val screenHeightDp: Dp)

val LocalWindow = compositionLocalOf { WindowInfo(Dp.Unspecified, Dp.Unspecified) }
