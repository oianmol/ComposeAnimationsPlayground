package dev.baseio.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

actual object PainterRes {

    @Composable
    actual fun ic_notification_bell(): Painter = painterResource(R.drawable.ic_notification_bell)

    @Composable
    actual fun airplane(): Painter = painterResource(R.drawable.airplane)

    @Composable
    actual fun clouds_center(): Painter = painterResource(R.drawable.clouds_center)

    @Composable
    actual fun clouds_right(): Painter = painterResource(R.drawable.clouds_right)

    @Composable
    actual fun clouds_left(): Painter = painterResource(R.drawable.clouds_left)

    @Composable
    actual fun ic_sun(): Painter = painterResource(R.drawable.ic_sun)

    @Composable
    actual fun twitter_icon(): Painter = painterResource(R.drawable.twitter_icon)

    @Composable
    actual fun spaceship(): Painter = painterResource(R.drawable.spaceship)

    @Composable
    actual fun spshipshadow(): Painter = painterResource(R.drawable.spshipshadow)

    @Composable
    actual fun notfound(): Painter = painterResource(R.drawable.notfound)

    @Composable
    actual fun githubavatar(): Painter = painterResource(R.drawable.githubavatar)

    @Composable
    actual fun avatarshadow(): Painter = painterResource(R.drawable.avatarshadow)

    @Composable
    actual fun background(): Painter = painterResource(R.drawable.background)

    @Composable
    actual fun deserthome1(): Painter = painterResource(R.drawable.deserthome1)

    @Composable
    actual fun ic_thumb(): Painter = painterResource(R.drawable.ic_thumb)

    @Composable
    actual fun ic_alarm(): Painter = painterResource(R.drawable.ic_alarm)

    @Composable
    actual fun ic_bed(): Painter = painterResource(R.drawable.ic_bed)
}