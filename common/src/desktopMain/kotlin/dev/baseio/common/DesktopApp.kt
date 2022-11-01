// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package dev.baseio.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun AppPreview() {
    App(WindowInfo(1024.dp,768.dp))
}