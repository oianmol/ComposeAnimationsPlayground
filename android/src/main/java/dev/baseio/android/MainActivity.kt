package dev.baseio.android

import dev.baseio.common.App
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import dev.baseio.common.WindowInfo
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val config = LocalConfiguration.current

            var rememberedComposeWindow by remember {
                mutableStateOf(WindowInfo(config.screenWidthDp.dp, config.screenHeightDp.dp))
            }

            LaunchedEffect(config) {
                snapshotFlow { config }.distinctUntilChanged().onEach {
                    rememberedComposeWindow = WindowInfo(it.screenWidthDp.dp, it.screenHeightDp.dp)
                }.launchIn(this)
            }

            App(rememberedComposeWindow)
        }
    }
}