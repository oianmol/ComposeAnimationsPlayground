// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.baseio.common.App
import dev.baseio.common.WindowInfo
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


fun main() = application {
    val windowState = rememberWindowState(size = DpSize(1024.dp,768.dp))

    Window(onCloseRequest = ::exitApplication, state = windowState) {
        var rememberedComposeWindow by remember(this.window) {
            mutableStateOf(WindowInfo(windowState.size.width, windowState.size.height))
        }
        LaunchedEffect(windowState) {
            snapshotFlow { windowState.size }
                .distinctUntilChanged()
                .onEach {
                    rememberedComposeWindow = WindowInfo(it.width, it.height)
                }
                .launchIn(this)
        }

        App(rememberedComposeWindow)
    }
}
