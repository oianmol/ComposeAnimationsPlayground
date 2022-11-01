package dev.baseio.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import dev.baseio.composeplayground.ui.AnimationsPager
import dev.baseio.composeplayground.ui.theme.ComposePlaygroundTheme

@Composable
fun App(rememberedComposeWindow: WindowInfo) {
    CompositionLocalProvider(
        LocalWindow provides rememberedComposeWindow
    ) {
        ComposePlaygroundTheme {
            Scaffold {
                AnimationsPager(Modifier.padding(it))
            }
        }
    }

}
