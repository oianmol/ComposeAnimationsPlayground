package dev.baseio.composeplayground.contributors

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun NetworkImageBox(modifier: Modifier, imageUrl: String)