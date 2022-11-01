package dev.baseio.common

import androidx.compose.ui.unit.dp

enum class WindowSize { Phones, SmallTablets, BigTablets, DesktopOne, DesktopTwo }

fun getWindowSizeClass(windowDpSize: WindowInfo): WindowSize = when {
    windowDpSize.screenWidthDp < 0.dp ->
        throw IllegalArgumentException("Dp value cannot be negative")

    windowDpSize.screenWidthDp < 600.dp -> WindowSize.Phones
    windowDpSize.screenWidthDp < 960.dp -> WindowSize.SmallTablets
    windowDpSize.screenWidthDp < 1024.dp -> WindowSize.BigTablets
    windowDpSize.screenWidthDp < 1366.dp -> WindowSize.DesktopOne
    else -> WindowSize.DesktopTwo
}