package dev.baseio.composeplayground.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.baseio.composeplayground.ui.animations.GlowingRingLoader
import dev.baseio.composeplayground.ui.animations.*
import dev.baseio.composeplayground.ui.animations.anmolverma.*
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.GoogleIO
import dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators.LoadingPreviewView
import dev.baseio.composeplayground.ui.animations.anmolverma.planetarysystem.PlanetarySystem
import dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh.PullToRefreshOne

@Composable
fun AnimationsPager(modifier: Modifier) {
    var currentAnimation by remember { mutableStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        animations[currentAnimation].invoke()

        IconButton({
            if (currentAnimation > 0) {
                currentAnimation = currentAnimation.dec()
            }
        }, modifier = Modifier.align(Alignment.CenterStart).background(Color.Black.copy(alpha = 0.4f,))) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }

        IconButton({
            if (currentAnimation < animations.size.minus(1)) {
                currentAnimation = currentAnimation.inc()
            }
        }, modifier = Modifier.align(Alignment.CenterEnd).background(Color.Black.copy(alpha = 0.4f,))) {
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }

    }
}

val animations = mutableListOf<@Composable () -> Unit>().apply {
    add {
        Box(modifier = Modifier.fillMaxSize()) {
            IOSSleepSchedule()
        }
    }
    add{
        LoadingPreviewView(Modifier.fillMaxSize().background(Color.White))
    }
    add {
        PullToRefreshOne()
    }
    add{
        RandomFlutterCopy()
    }
    add {
        Box(Modifier.fillMaxSize()) {
            GlowingRingLoader(Modifier.align(Alignment.Center))
        }
    }
    add {
        Box(Modifier.fillMaxSize()) {
            YahooWeatherAndSun(Modifier.align(Alignment.Center))
        }
    }
    add {
        TwitterSplashAnimation()
    }
    add {
        AndroidMadSkills()
    }
    add {
        ShootingStarsAnimation()
    }
    add {
        NetflixIntroAnimation()
    }
    add {
        GoogleIO()
    }
    add {
        Box(modifier = Modifier.fillMaxSize()) {
            Github404()
        }
    }
    add {
        Box(modifier = Modifier.fillMaxSize()) {
            ScalingRotatingLoader()
        }
    }
    add {
        Box(Modifier.fillMaxSize()) {
            PlanetarySystem(Modifier.align(Alignment.Center))
        }
    }
    add {
        Box(Modifier.fillMaxSize()) {
            LikeAnimation(Modifier.align(Alignment.Center))
        }
    }
    add {
        SlackAnimation()
    }
    add {
        DiwaliFlame()
    }
    add {
        Box(Modifier.fillMaxSize()) {
            ChatMessageReactions(Modifier.align(Alignment.Center))
        }
    }
    add {
        Box(Modifier.fillMaxSize()) {
            MenuToClose(Modifier.align(Alignment.Center))
        }
    }
    add {
        Box(Modifier.fillMaxSize()) {
            BellAnimation(Modifier.align(Alignment.Center))
        }
    }
    add {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            DuolingoBird(Modifier.align(Alignment.Center))
        }
    }
}