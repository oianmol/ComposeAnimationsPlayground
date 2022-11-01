package dev.baseio.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

expect object PainterRes {
    @Composable
    fun ic_alarm(): Painter

    @Composable
    fun ic_bed(): Painter

    @Composable
    fun ic_thumb(): Painter

    @Composable
    fun spaceship(): Painter

    @Composable
    fun spshipshadow(): Painter

    @Composable
    fun notfound(): Painter

    @Composable
    fun githubavatar(): Painter

    @Composable
    fun avatarshadow(): Painter

    @Composable
    fun background(): Painter

    @Composable
    fun deserthome1(): Painter

    @Composable
    fun twitter_icon(): Painter

    @Composable
    fun ic_sun(): Painter

    @Composable
    fun clouds_center(): Painter

    @Composable
    fun clouds_right(): Painter

    @Composable
    fun clouds_left(): Painter

    @Composable
    fun airplane(): Painter

    @Composable
    fun ic_notification_bell(): Painter
}