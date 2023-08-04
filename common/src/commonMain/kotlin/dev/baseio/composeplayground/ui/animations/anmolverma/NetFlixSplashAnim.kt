package dev.baseio.composeplayground.ui.animations.anmolverma

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val netflixColor = Color(0xffe40913)
const val TOTAL_NF_TIME = 2000 / 7

@Composable
fun NetflixSplashAnim() {
    var startE by remember { mutableStateOf(false) }
    var startT by remember { mutableStateOf(false) }
    var startF by remember { mutableStateOf(false) }
    var startL by remember { mutableStateOf(false) }
    var startI by remember { mutableStateOf(false) }
    var startX by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        launch {
            delay(TOTAL_NF_TIME.toLong())
            startE = true
        }
        launch {
            delay(TOTAL_NF_TIME.toLong().times(2))
            startT = true
        }
        launch {
            delay(TOTAL_NF_TIME.toLong().times(3))
            startF = true
        }
        launch {
            delay(TOTAL_NF_TIME.toLong().times(4))
            startL = true
        }
        launch {
            delay(TOTAL_NF_TIME.toLong().times(5))
            startI = true
        }
        launch {
            delay(TOTAL_NF_TIME.toLong().times(6))
            startX = true
        }
    }

    val space1 by animateDpAsState(
        if (startE) 15.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME),
    )

    val space2 by animateDpAsState(
        if (startT) 15.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME),
    )

    val space3 by animateDpAsState(
        if (startF) 15.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME),
    )
    val space4 by animateDpAsState(
        if (startL) 15.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME),
    )
    val space5 by animateDpAsState(
        if (startI) 15.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME),
    )
    val space6 by animateDpAsState(
        if (startX) 25.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME),
    )


    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // NETFLIX
        Box(Modifier.align(Alignment.Center).height(60.dp)) {
            Row {
                DrawN {
                }
                Spacer(Modifier.width(space1))
                DrawF(isE = true, startAnim = startE) {
                }
                Spacer(Modifier.width(space2))
                DrawT(startT) {
                }
                Spacer(Modifier.width(space3))
                DrawF(startAnim = startF) {
                }
                Spacer(Modifier.width(space4))
                DrawL(startL) {
                }
                Spacer(Modifier.width(space5))
                DrawI(startI) {
                }
                Spacer(Modifier.width(space6))
                DrawX(startX) {

                }
            }
        }
    }
}

@Composable
fun DrawX(startX: Boolean, complete: () -> Unit) {
    var start by remember { mutableStateOf(false) }
    LaunchedEffect(startX) {
        start = startX
    }
    val size by animateFloatAsState(
        if (start) 1f else 0f,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            complete()
        })
    val width by animateDpAsState(
        if (start) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    Box {
        // X
        Block(
            Modifier.width(width)
                .fillMaxHeight(size)
                .graphicsLayer {
                    this.rotationZ = 150f
                }
        )
        Block(
            Modifier.width(width)
                .fillMaxHeight(size)
                .graphicsLayer {
                    this.rotationZ = 35f
                }
        )
    }
}

@Composable
fun DrawI(startI: Boolean, complete: () -> Unit) {
    // I
    var start by remember { mutableStateOf(false) }
    LaunchedEffect(startI) {
        start = startI
    }
    val size1 by animateFloatAsState(
        if (start) 0.95f else 0f,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            complete()
        })
    val width by animateDpAsState(
        if (start) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    Block(
        Modifier.width(width)
            .fillMaxHeight(size1)
    )
}

@Composable
fun DrawL(startL: Boolean, complete: () -> Unit) {
    var start by remember { mutableStateOf(false) }
    var start2 by remember { mutableStateOf(false) }

    LaunchedEffect(startL) {
        start = startL
    }

    val size1 by animateFloatAsState(
        if (start) 0.8f else 0f,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            start2 = true
        })
    val width1 by animateDpAsState(
        if (start) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    val size2 by animateDpAsState(
        if (start2) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            complete()
        })
    val width2 by animateDpAsState(
        if (start2) 30.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    Column(horizontalAlignment = Alignment.Start) {
        // L
        Block(
            Modifier.width(width1)
                .fillMaxHeight(size1)
        )
        Block(
            Modifier.width(width2)
                .height(size2)
        )
    }
}

@Composable
fun DrawF(isE: Boolean = false, startAnim: Boolean = false, completed: () -> Unit) {
    var start by remember { mutableStateOf(false) }
    var start2 by remember { mutableStateOf(false) }
    var start3 by remember { mutableStateOf(false) }
    var start4 by remember { mutableStateOf(false) }

    LaunchedEffect(startAnim) {
        start = startAnim
    }

    val size1 by animateFloatAsState(
        if (start) 1f else 0f,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            start2 = true
        })
    val width1 by animateDpAsState(
        if (start) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    val size2 by animateDpAsState(
        if (start2) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            start3 = true
        })
    val width2 by animateDpAsState(
        if (start2) 20.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    val size3 by animateDpAsState(
        if (start3) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            if (isE) {
                start4 = true
            } else {
                completed()
            }
        })
    val width3 by animateDpAsState(
        if (start3) 20.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    val size4 by animateDpAsState(
        if (start4) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            completed()
        })
    val width4 by animateDpAsState(
        if (start4) 20.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    Row {
        // E
        Block(
            Modifier.width(width1)
                .fillMaxHeight(size1)
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Block(
                Modifier.width(width2)
                    .height(size2)
            )
            Block(
                Modifier.width(width3)
                    .height(size3)
            )
            Block(
                Modifier.width(width4)
                    .height(size4),
                color = if (isE) netflixColor else Color.Black
            )
        }
    }
}

@Composable
fun DrawT(startT: Boolean = false, complete: () -> Unit) {
    var start by remember { mutableStateOf(false) }
    var start2 by remember { mutableStateOf(false) }

    LaunchedEffect(startT) {
        start = startT
    }

    val size1 by animateDpAsState(
        if (start) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            start2 = true
        })
    val width1 by animateDpAsState(
        if (start) 30.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    val size2 by animateFloatAsState(
        if (start2) 1f else 0f,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            complete()
        })
    val width2 by animateDpAsState(
        if (start2) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // T
        Block(
            Modifier.width(width1)
                .height(size1)
        )
        Block(
            Modifier.width(width2)
                .fillMaxHeight(size2)
        )
    }
}

@Composable
private fun DrawN(completed: () -> Unit) {
    var startOne by remember { mutableStateOf(false) }
    var startTwo by remember { mutableStateOf(false) }
    var startThree by remember { mutableStateOf(false) }

    val size1 by animateFloatAsState(
        if (startOne) 1f else 0f,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            startTwo = true
        })
    val width1 by animateDpAsState(
        if (startOne) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    val size2 by animateFloatAsState(
        if (startTwo) 1f else 0f,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            startThree = true
        })
    val width2 by animateDpAsState(
        if (startTwo) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    val size3 by animateFloatAsState(
        if (startThree) 1f else 0f,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
        finishedListener = {
            completed()
        })
    val width3 by animateDpAsState(
        if (startThree) 10.dp else 0.dp,
        animationSpec = tween(TOTAL_NF_TIME, easing = LinearEasing),
    )
    LaunchedEffect(Unit) {
        startOne = true
    }
    Row {
        // N
        Block(
            Modifier.width(width1)
                .fillMaxHeight(size1)
        )
        Block(
            Modifier.width(width2)
                .fillMaxHeight(size2)
                .graphicsLayer {
                    this.rotationZ = 160f
                    this.rotationY = 360f
                }
        )
        Block(
            Modifier.width(width3)
                .fillMaxHeight(size3)
        )
    }
}

@Composable
fun Block(modifier: Modifier, color: Color = netflixColor) {
    Box(
        modifier.background(color).graphicsLayer { shadowElevation = 10f }
    )
}