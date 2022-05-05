package dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingIndicator(
    animation: LoadingAnimation,
    size: Int,
    speed: Double, color: Color = Color.Black
) {
    when (animation) {
        LoadingAnimation.threeBalls -> LoadingThreeBalls(color = color, size = size, speed = speed)
        LoadingAnimation.threeBallsRotation -> LoadingThreeBallsRotation(
            color = color,
            size = size,
            speed = speed
        )
        LoadingAnimation.threeBallsBouncing -> LoadingThreeBallsBouncing(
            color = color,
            size = size,
            speed = speed
        )
        LoadingAnimation.pulse -> LoadingPulse(color = color, size = size, speed = speed)
        /* LoadingAnimation.threeBallsTriangle -> LoadingThreeBallsTriangle(
           color = color,
           size = size,
           speed = speed
       )
       LoadingAnimation.fiveLines -> LoadingFiveLines(color = color, size = size, speed = speed)
       LoadingAnimation.fiveLinesChronological -> LoadingFiveLinesChronological(
           color = color,
           size = size,
           speed = speed
       )
       LoadingAnimation.fiveLinesWave -> LoadingFiveLinesWave(
           color = color,
           size = size,
           speed = speed
       )
       LoadingAnimation.fiveLinesCenter -> LoadingFiveLinesCenter(
           color = color,
           size = size,
           speed = speed
       )
       LoadingAnimation.fiveLinesPulse -> LoadingFiveLinesPulse(
           color = color,
           size = size,
           speed = speed
       )
       LoadingAnimation.pulseOutline -> LoadingPulseOutline(
           color = color,
           size = size,
           speed = speed
       )
       LoadingAnimation.pulseOutlineRepeater -> LoadingPulseOutlineRepeater(
           color = color,
           size = size,
           speed = speed
       )
       LoadingAnimation.circleTrim -> LoadingCircleTrim(color = color, size = size, speed = speed)
       LoadingAnimation.circleRunner -> LoadingCircleRunner(
           color = color,
           size = size,
           speed = speed
       )
       LoadingAnimation.circleBlinks -> LoadingCircleBlinks(
           color = color,
           size = size,
           speed = speed
       )
       LoadingAnimation.circleBars -> LoadingCircleBars(color = color, size = size, speed = speed)
       LoadingAnimation.doubleHelix -> LoadingDoubleHelix(
           color = color,
           size = size,
           speed = speed
       )
       LoadingAnimation.bar -> LoadingBar(color = color, size = size, speed = speed)
       LoadingAnimation.barStripes -> LoadingBarStripes(color = color, size = size, speed = speed)
       LoadingAnimation.text -> LoadingText(color = color, size = size, speed = speed)
       LoadingAnimation.heart -> LoadingHeart(color = color, size = size, speed = speed)*/
    }
}

enum class LoadingAnimation {
    threeBalls,
    threeBallsRotation,
    threeBallsBouncing,
    threeBallsTriangle,
    fiveLines,
    fiveLinesChronological,
    fiveLinesWave,
    fiveLinesCenter,
    fiveLinesPulse,
    pulse,
    pulseOutline,
    pulseOutlineRepeater,
    circleTrim,
    circleRunner,
    circleBlinks,
    circleBars,
    doubleHelix,
    bar,
    barStripes,
    text,
    heart
}

enum class Speed(val factor: Double) {
    slow(1.0),
    normal(0.5),
    fast(0.25)
}

enum class Size(val factor: Int) {
    small(25),
    medium(50),
    large(100),
}