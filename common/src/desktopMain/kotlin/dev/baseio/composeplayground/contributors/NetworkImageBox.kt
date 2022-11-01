package dev.baseio.composeplayground.contributors

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource

@Composable
actual fun NetworkImageBox(modifier: Modifier, imageUrl: String) {
    KamelImage(
        lazyPainterResource(imageUrl),
        modifier = modifier.clip(RoundedCornerShape(25)),
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )
}