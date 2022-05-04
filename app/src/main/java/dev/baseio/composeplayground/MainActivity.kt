package dev.baseio.composeplayground

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.accompanist.insets.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import dev.baseio.composeplayground.ui.animations.*
import dev.baseio.composeplayground.ui.animations.anmolverma.BellAnimation
import dev.baseio.composeplayground.ui.animations.anmolverma.GramophoneDisc
import dev.baseio.composeplayground.ui.animations.anmolverma.ShootingStarsAnimation
import dev.baseio.composeplayground.ui.animations.anmolverma.SlackAnimation
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.GoogleIO
import dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators.LoadingAnimation
import dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators.LoadingIndicator
import dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators.Size
import dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators.Speed
import dev.baseio.composeplayground.ui.animations.anmolverma.planetarysystem.PlanetarySystem
import dev.baseio.composeplayground.ui.animations.anmolverma.pulltorefresh.PullToRefreshOne
import dev.baseio.composeplayground.ui.theme.ComposePlaygroundTheme
import dev.baseio.composeplayground.ui.theme.Typography

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalPagerApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    window.apply {
      clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
      clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
      addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        decorView.systemUiVisibility =
          View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
      }
      statusBarColor = Color.TRANSPARENT
      navigationBarColor = Color.TRANSPARENT
    }
    super.onCreate(savedInstanceState)

    setContent {
      ComposePlaygroundTheme {
        // A surface container using the 'background' color from the theme
        MaterialTheme() {
          ProvideWindowInsets() {
            Surface(color = MaterialTheme.colors.background) {
              AnimationsPager()
            }
          }
        }
      }
    }
  }

  @OptIn(ExperimentalPagerApi::class)
  @Composable
  private fun AnimationsPager() {
    val pagerState = rememberPagerState()
    Box(
      modifier = Modifier
        .fillMaxSize()
    ) {
      HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        count = 19, state = pagerState,
      ) { page ->
        // Our page content
        when (page) {
          4 -> {
            PullToRefreshOne()
          }
          7 -> {
            Box(Modifier.fillMaxSize()) {
              GlowingRingLoader(Modifier.align(Alignment.Center))
            }
          }
          6 -> {
            Box(Modifier.fillMaxSize()) {
              YahooWeatherAndSun(Modifier.align(Alignment.Center))
            }
          }
          10 -> {
            Box(modifier = Modifier.fillMaxSize()) {
              IOSSleepSchedule()
            }
          }
          12 -> {
            TwitterSplashAnimation()
          }
          13 -> {
            AndroidMadSkills()
          }
          16 -> {
            ShootingStarsAnimation()
          }
          0 -> {
            GoogleIO()
          }
          17 -> {
            NetflixIntroAnimation()
          }
          11 -> {
            Box(modifier = Modifier.fillMaxSize()) {
              Github404(Modifier)
            }
          }
          9 -> {
            Box(modifier = Modifier.fillMaxSize()) {
              ScalingRotatingLoader()
            }
          }
          8 -> {
            Box(Modifier.fillMaxSize()) {
              PlanetarySystem(Modifier.align(Alignment.Center))
            }
          }
          15 -> {
            Box(Modifier.fillMaxSize()) {
              LikeAnimation(Modifier.align(Alignment.Center))
            }
          }
          1 -> {
            SlackAnimation()
          }
          2 -> {
//            GramophoneDisc()
          }
          14 -> {
            Box(Modifier.fillMaxSize()) {
              ChatMessageReactions(Modifier.align(Alignment.Center))
            }
          }
          3 -> {
            Box(Modifier.fillMaxSize()) {
              MenuToClose(Modifier.align(Alignment.Center))
            }
          }
          5 -> {
            Box(Modifier.fillMaxSize()) {
              BellAnimation(Modifier.align(Alignment.Center))
            }
          }
          18 -> {
            Column(
              Modifier
                .verticalScroll(rememberScrollState())
                .padding(24.dp)){
              LoadingAnimation.values().forEach { loadingAnim->
                Column(
                  Modifier) {
                  Column {
                    Text(text = "Sizes", style = Typography.subtitle1.copy(fontWeight = FontWeight.Bold))

                    Size.values().forEach { size ->
                      Column(Modifier.border(1.dp, androidx.compose.ui.graphics.Color.Black).padding(8.dp)) {
                        LoadingIndicator(loadingAnim, size.factor, Speed.normal.factor)
                        Text(size.toString(), style = Typography.caption)
                      }
                    }
                  }
                  Divider()
                  Text(text = "Speeds", style = Typography.subtitle1.copy(fontWeight = FontWeight.Bold))
                  Speed.values().forEach { speed ->
                    Column(Modifier.border(1.dp, androidx.compose.ui.graphics.Color.Black).padding(8.dp)) {
                      LoadingIndicator(loadingAnim, Size.medium.factor, speed.factor)
                      Text(speed.toString(), style = Typography.caption)
                    }
                  }
                }
              }
            }
          }
        }
      }
      HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(16.dp)
      )
    }
  }
}

fun hideSystemBars(window: Window) {
  val windowInsetsController =
    ViewCompat.getWindowInsetsController(window.decorView) ?: return
  // Configure the behavior of the hidden system bars
  windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
  windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
}
