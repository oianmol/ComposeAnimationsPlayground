package dev.baseio.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

actual object PainterRes {
    @Composable
    actual fun ic_notification_bell(): Painter = painterResource("images/ic_notification_bell.xml")

    @Composable
    actual fun ic_alarm(): Painter = painterResource("images/ic_alarm.xml")

    @Composable
    actual fun ic_bed(): Painter = painterResource("images/ic_bed.xml")

    @Composable
    actual fun ic_thumb(): Painter = painterResource("images/ic_thumb.xml")

    @Composable
    actual fun spaceship(): Painter = painterResource("images/spaceship.png")

    @Composable
    actual fun spshipshadow(): Painter = painterResource("images/spshipshadow.png")

    @Composable
    actual fun notfound(): Painter = painterResource("images/notfound.png")

    @Composable
    actual fun githubavatar(): Painter = painterResource("images/githubavatar.png")

    @Composable
    actual fun avatarshadow(): Painter = painterResource("images/avatarshadow.png")

    @Composable
    actual fun background(): Painter = painterResource("images/background.jpeg")

    @Composable
    actual fun deserthome1(): Painter = painterResource("images/deserthome1.png")

    @Composable
    actual fun twitter_icon(): Painter = painterResource("images/twitter_icon.xml")

    @Composable
    actual fun ic_sun(): Painter = painterResource("images/ic_sun.xml")

    @Composable
    actual fun clouds_center(): Painter = painterResource("images/clouds_center.png")

    @Composable
    actual fun clouds_right(): Painter = painterResource("images/clouds_right.png")

    @Composable
    actual fun clouds_left(): Painter = painterResource("images/clouds_left.png")

    @Composable
    actual fun airplane(): Painter = painterResource("images/airplane.png")
}