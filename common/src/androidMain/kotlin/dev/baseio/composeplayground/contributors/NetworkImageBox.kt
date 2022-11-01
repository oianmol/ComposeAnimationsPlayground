package dev.baseio.composeplayground.contributors

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation

@Composable
actual fun NetworkImageBox(modifier: Modifier, imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
                transformations(RoundedCornersTransformation(12.0f, 12.0f, 12.0f, 12.0f))
            }).build()
        ),
        contentDescription = null,
        modifier = modifier
    )
}