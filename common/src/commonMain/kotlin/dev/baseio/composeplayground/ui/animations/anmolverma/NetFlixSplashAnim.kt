package dev.baseio.composeplayground.ui.animations.anmolverma

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

val netflixColor = Color(0xffe40913)

@Composable
fun NetFlixSplashAnim() {
    var startE by remember { mutableStateOf(false) }
    var startT by remember { mutableStateOf(false) }
    var startF by remember { mutableStateOf(false) }
    var startL by remember { mutableStateOf(false) }
    var startI by remember { mutableStateOf(false) }
    var startX by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // NETFLIX
        Box(Modifier.align(Alignment.Center).height(60.dp)) {
            Row {
                DrawN {
                    startE = true
                }
                Spacer(Modifier.width(10.dp))
                DrawF(isE = true, startAnim = startE) {
                    startT = true
                }
                Spacer(Modifier.width(10.dp))
                DrawT(startT) {
                    startF = true
                }
                Spacer(Modifier.width(10.dp))
                DrawF(startAnim = startF) {
                    startL = true
                }
                Spacer(Modifier.width(10.dp))
                DrawL(startL) {
                    startI = true
                }
                Spacer(Modifier.width(10.dp))
                DrawI(startI) {
                    startX = true
                }
                Spacer(Modifier.width(25.dp))
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
        animationSpec = tween(200),
        finishedListener = {
            complete()
        })
    Box {
        // X
        Block(
            Modifier.width(10.dp)
                .fillMaxHeight(size)
                .graphicsLayer {
                    this.rotationZ = 150f
                }
        )
        Block(
            Modifier.width(10.dp)
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
        animationSpec = tween(200),
        finishedListener = {
            complete()
        })
    Block(
        Modifier.width(10.dp)
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
        animationSpec = tween(200),
        finishedListener = {
            start2 = true
        })
    val size2 by animateDpAsState(
        if (start2) 10.dp else 0.dp,
        animationSpec = tween(200),
        finishedListener = {
            complete()
        })
    Column(horizontalAlignment = Alignment.Start) {
        // L
        Block(
            Modifier.width(10.dp)
                .fillMaxHeight(size1)
        )
        Block(
            Modifier.width(30.dp)
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
        animationSpec = tween(200),
        finishedListener = {
            start2 = true
        })
    val size2 by animateDpAsState(
        if (start2) 10.dp else 0.dp,
        animationSpec = tween(200),
        finishedListener = {
            start3 = true
        })
    val size3 by animateDpAsState(
        if (start3) 10.dp else 0.dp,
        animationSpec = tween(200),
        finishedListener = {
            if (isE) {
                start4 = true
            } else {
                completed()
            }
        })
    val size4 by animateDpAsState(
        if (start4) 10.dp else 0.dp,
        animationSpec = tween(200),
        finishedListener = {
            completed()
        })

    Row {
        // E
        Block(
            Modifier.width(10.dp)
                .fillMaxHeight(size1)
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Block(
                Modifier.width(20.dp)
                    .height(size2)
            )
            Block(
                Modifier.width(20.dp)
                    .height(size3)
            )
            Block(
                Modifier.width(20.dp)
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
        animationSpec = tween(200),
        finishedListener = {
            start2 = true
        })
    val size2 by animateFloatAsState(
        if (start2) 1f else 0f,
        animationSpec = tween(200),
        finishedListener = {
            complete()
        })
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // T
        Block(
            Modifier.width(30.dp)
                .height(size1)
        )
        Block(
            Modifier.width(10.dp)
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
        animationSpec = tween(200),
        finishedListener = {
            startTwo = true
        })
    val size2 by animateFloatAsState(
        if (startTwo) 1f else 0f,
        animationSpec = tween(200),
        finishedListener = {
            startThree = true
        })
    val size3 by animateFloatAsState(
        if (startThree) 1f else 0f,
        animationSpec = tween(200),
        finishedListener = {
            completed()
        })
    LaunchedEffect(Unit) {
        startOne = true
    }
    Row {
        // N
        Block(
            Modifier.width(10.dp)
                .fillMaxHeight(size1)
        )
        Block(
            Modifier.width(10.dp)
                .fillMaxHeight(size2)
                .graphicsLayer {
                    this.rotationZ = 160f
                    this.rotationY = 360f
                }
        )
        Block(
            Modifier.width(10.dp)
                .fillMaxHeight(size3)
        )
    }
}

@Composable
fun Block(modifier: Modifier, color: Color = netflixColor) {
    Box(
        modifier.background(color)
    )
}