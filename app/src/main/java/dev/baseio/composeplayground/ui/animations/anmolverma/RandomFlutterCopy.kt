package dev.baseio.composeplayground.ui.animations.anmolverma

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import dev.baseio.composeplayground.ui.animations.BackgroundImageGithub
import dev.baseio.composeplayground.ui.animations.GithubAvatar
import dev.baseio.composeplayground.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun RandomFlutterCopy() {

    val infiniteTransition = rememberInfiniteTransition()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    val offY by infiniteTransition.animateFloat(
        initialValue = 150f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(durationMillis = 1500, easing = LinearOutSlowInEasing)
        )
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true, block = {
        scope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                bottomSheetScaffoldState.bottomSheetState.expand()
            } else {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }
    })
    val pagerState = rememberPagerState()

    var height by remember {
        mutableStateOf((400 + pagerState.currentPage * 60).toFloat())
    }

    var buttonBottomOffset by remember {
        mutableStateOf(50f)
    }

    var getStartedWidth by remember {
        mutableStateOf(50f)
    }

    var offset by remember {
        mutableStateOf(0f)
    }

    BottomSheetScaffold(
        sheetContent = {
            Box(modifier = Modifier) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height.dp),
                    count = 2, state = pagerState,
                ) { page ->
                    buttonBottomOffset =
                        32 + (1 - calculateCurrentOffsetForPage(page).absoluteValue).times(48)
                    height = ((400 + 1 - abs(
                        calculateCurrentOffsetForPage(page)
                    ) * 60))
                    getStartedWidth =
                        120 + (1 - calculateCurrentOffsetForPage(page).absoluteValue) * 12
                    offset = 1 - abs(calculateCurrentOffsetForPage(page))
                    when (page) {
                        0 -> {
                            CardOne(
                                Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(top = 24.dp, start = 12.dp)
                            )
                        }
                        1 -> {
                            CardTwo(
                                Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(top = 24.dp, start = 12.dp)
                            )
                        }
                    }
                }
                /* Add code later */

                GetStartedButton(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .width(getStartedWidth.dp)
                        .padding(
                            end = 16.dp,
                            bottom = buttonBottomOffset.dp
                        ),
                    pagerState,
                    scope
                )


                Text(
                    text = "Already have an account ? Sign in...",
                    style = Typography.subtitle1.copy(color = Color.Gray),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .alpha(offset)
                )
            }
        },
        sheetShape = RoundedCornerShape(topStartPercent = 15, topEndPercent = 15),
        sheetBackgroundColor = Color.White,
        scaffoldState = bottomSheetScaffoldState,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            BackgroundImageGithub(bgImageScaleFactor = 1f)
            GithubAvatar(githubAvatarX = 80f, githubAvatarY = offY)

        }
    }


}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GetStartedButton(
    modifier: Modifier,
    pagerState: PagerState,
    scope: CoroutineScope,
) {
    Box(
        modifier = modifier
            .clickable {
                scope.launch {
                    pagerState.animateScrollToPage(1)
                }
            }
            .background(
                Brush.linearGradient(
                    colorStops = arrayOf(
                        Pair(
                            0.4f,
                            Color(239, 104, 80)
                        ),
                        Pair(
                            0.8f,
                            Color(139, 33, 146)
                        )
                    )
                ), shape = RoundedCornerShape(42)
            ),
    ) {

        Text(
            text = "Get Started",
            style = TextStyle(color = Color.White),
            modifier = Modifier.padding(16.dp)
        )

    }
}

@Composable
fun CardTwo(modifier: Modifier) {

    Column(modifier) {
        Text(
            text = "Create an account",
            style = Typography.h4.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Username()
        Username()
    }
}

@Composable
fun Username() {

    var email by remember {
        mutableStateOf("")
    }

    TextField(
        value = email,
        onValueChange = {
            email = it
        },
        Modifier
            .fillMaxWidth(), label = {
            Text(
                text = "Email",
                style = MaterialTheme.typography.body2.copy(color = Color.Black)
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onNext = {
            },
        ),
        leadingIcon = {
            Image(
                imageVector = Icons.Default.Email,
                contentDescription = "Email"
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.White,
        ),
        maxLines = 1,
        singleLine = true
    )
}


@Composable
fun CardOne(modifier: Modifier) {
    Column(modifier) {
        Text(
            text = "Find Trending\nGithub repositories",
            style = Typography.h4.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Get Involved with open-source code.",
            style = Typography.h6.copy(color = Color.Gray),
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview
@Composable
fun RandomFlutterCopyPreview() {
    MaterialTheme() {
        RandomFlutterCopy()
    }
}