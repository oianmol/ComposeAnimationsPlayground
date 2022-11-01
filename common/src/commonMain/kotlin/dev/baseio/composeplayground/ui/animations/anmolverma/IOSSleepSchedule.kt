package dev.baseio.composeplayground.ui.animations

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import dev.baseio.common.LocalWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.baseio.common.PainterRes
import dev.baseio.common.WindowSize
import dev.baseio.common.getWindowSizeClass
import dev.baseio.composeplayground.ui.animations.anmolverma.drawText
import dev.baseio.composeplayground.ui.animations.anmolverma.googleio2022.PaintMode
import dev.baseio.composeplayground.ui.animations.anmolverma.toRadians
import dev.baseio.composeplayground.ui.theme.Typography
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

val offGray = Color(45, 44, 46)
val textSecondary = Color(157, 156, 167)

@Composable
fun PreviewIOSSleepSchedule() {
    MaterialTheme() {
        IOSSleepSchedule()
    }
}

@Composable
fun IOSSleepSchedule() {

    val windowSize = getWindowSizeClass(LocalWindow.current)
    val now = remember {
        LocalDate(2022, 1, 1)
    }

    var startTime by remember {
        mutableStateOf(LocalDateTime(now, LocalTime(22, 0)))
    }

    var endTime by remember {
        mutableStateOf(LocalDateTime(now.plus(DatePeriod(days = 1)), LocalTime(6, 0)))
    }

    when (windowSize) {
        WindowSize.Phones, WindowSize.SmallTablets -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().background(offGray)
        ) {
            StartEndTime(startTime, endTime)

            TouchMoveControlTrack(Modifier.weight(1f), startTime, endTime, { time ->
                startTime = time
            }) { time ->
                endTime = time
            }
            MeetsSleepGoal(startTime, endTime)

        }

        else -> {
            Row(
                modifier = Modifier.fillMaxSize().background(offGray),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Spacer(Modifier.width(16.dp))
                TouchMoveControlTrack(Modifier.fillMaxHeight().weight(1f), startTime, endTime, { time ->
                    startTime = time
                }) { time ->
                    endTime = time
                }
                Spacer(Modifier.width(16.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)
                ) {
                    StartEndTime(startTime, endTime)
                    MeetsSleepGoal(startTime, endTime)
                }
                Spacer(Modifier.width(16.dp))
            }
        }
    }
}

@Composable
private fun MeetsSleepGoal(startTime: LocalDateTime, endTime: LocalDateTime) {
    val duration = startTime.toInstant(TimeZone.UTC).periodUntil(
        endTime.toInstant(TimeZone.UTC),
        TimeZone.UTC
    )

    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${duration.hours}h ${duration.minutes}m",
            style = Typography.h5.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "This schedule meets your sleep goal.",
            style = Typography.subtitle1.copy(color = textSecondary)
        )
    }
}

@Composable
private fun StartEndTime(startTime: LocalDateTime, endTime: LocalDateTime) {
    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
        VerticalGroupTime(isStart = true, startTime, endTime)
        VerticalGroupTime(isStart = false, startTime = startTime, endTime = endTime)
    }
}

const val DEGREE_IN_HOUR = 15f
const val MINUTES_IN_HOUR = 60


fun convertHourToAngle(startTime: LocalDateTime, endTime: LocalDateTime): Float {
    val angle =
        startTime.hour.times(DEGREE_IN_HOUR) + startTime.minute.times(DEGREE_IN_HOUR / MINUTES_IN_HOUR)
    val endAngle =
        endTime.hour.times(DEGREE_IN_HOUR) + endTime.minute.times(DEGREE_IN_HOUR / MINUTES_IN_HOUR)
    return endAngle.minus(angle)
}


fun convertTimeToAngle(startTime: LocalDateTime): Float {
    return startTime.hour.times(DEGREE_IN_HOUR) +
            startTime.minute.times(DEGREE_IN_HOUR / MINUTES_IN_HOUR)
}

//https://en.wikipedia.org/wiki/Clock_angle_problem
fun convertAngleToLocalDateTime(startAngle: Float): LocalDateTime {
    var startAngle = startAngle
    while (startAngle > 360) {
        startAngle = startAngle.minus(360f)
    }

    var decimalValue = startAngle.div(DEGREE_IN_HOUR)
    if (decimalValue < 0) decimalValue += 12.0f
    val hours = decimalValue.toInt()
    val minutes = (decimalValue * 60).toInt() % 60
    return LocalDateTime(LocalDate(2022, 1, 1), LocalTime(hours, minutes))
}

@Composable
private fun TouchMoveControlTrack(
    modifier: Modifier,
    sTime: LocalDateTime,
    enTime: LocalDateTime,
    startTimeUpdateInvoker: (LocalDateTime) -> Unit,
    endTimeUpdateInvoker: (LocalDateTime) -> Unit
) {

    var updatedStartTime by remember {
        mutableStateOf(sTime)
    }

    var updatedEndTime by remember {
        mutableStateOf(enTime)
    }

    var startIconAngle by remember {
        mutableStateOf(convertTimeToAngle(updatedStartTime))
    }
    var endIconAngle by remember {
        mutableStateOf(convertTimeToAngle(updatedEndTime))
    }

    var shapeCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var radius by remember {
        mutableStateOf(0f)
    }


    val constraintsScope = rememberCoroutineScope()

    val reduceOffsetIcon = with(LocalDensity.current) {
        24.dp.toPx()
    }

    val clockSize = 300.dp
    val clockRadiusDp = clockSize.div(2)

    val clockRadius = with(LocalDensity.current) {
        clockRadiusDp.toPx()
    }

    val knobTrackStrokeWidth = clockRadius.div(2.4f)
    val knobStrokeWidth = clockRadius.div(4)

    val haptic = LocalHapticFeedback.current

    Box(modifier) {
        Canvas(modifier = Modifier.size(clockSize)
            .align(Alignment.Center),
            onDraw = {
                drawBlackKnobBackground(knobTrackStrokeWidth)
                drawClockCircle(clockRadius.div(1.2f), shapeCenter)
            })

        DrawTicks(clockRadiusDp)

        Canvas(modifier = Modifier
            .size(clockSize)
            .align(Alignment.Center)
            .pointerInput(Unit) {
                var timeAtTouchScroll: LocalDateTime? = null
                var angleOnTouchWhenStarted: Float? = null
                var isStartWithStartIcon: Boolean? = null
                var isStartWithEndIcon: Boolean? = null
                constraintsScope.launch {
                    detectDragGestures(
                        onDragEnd = {
                            timeAtTouchScroll = null
                        },
                        onDragStart = { offset ->
                            angleOnTouchWhenStarted = getRotationAngleWithfixArcThreeOClock(offset, shapeCenter)
                            timeAtTouchScroll = convertAngleToLocalDateTime(angleOnTouchWhenStarted!!)
                            isStartWithStartIcon = timeAtTouchScroll!!.toInstant(TimeZone.UTC).periodUntil(
                                updatedStartTime.toInstant(
                                    TimeZone.UTC
                                ), TimeZone.UTC
                            ).minutes in -30..30
                            isStartWithEndIcon = timeAtTouchScroll!!.toInstant(TimeZone.UTC).periodUntil(
                                correctEndTimeNotConcerningDay(
                                    timeAtTouchScroll,
                                    updatedEndTime
                                ), TimeZone.UTC
                            ).minutes in -30..30
                        },
                        onDrag = { change, _ ->
                            var startAngleKnob = getRotationAngleWithfixArcThreeOClock(
                                change.position,
                                shapeCenter
                            ).adjustWithin360()

                            val endingAngle: Float

                            when {
                                isStartWithStartIcon == true -> {
                                    // user touched the start icon.
                                    endingAngle = convertTimeToAngle(updatedEndTime)
                                }

                                isStartWithEndIcon == true -> {
                                    endingAngle = startAngleKnob
                                    startAngleKnob = convertTimeToAngle(updatedStartTime)
                                }

                                else -> {
                                    val angleTouchStarted = convertTimeToAngle(timeAtTouchScroll!!)
                                    val angleSweepedAfterMove = startAngleKnob.minus(angleTouchStarted).times(0.5f)
                                    // TODO fix this check so we can calculate the correct startAngle for knob
                                    startAngleKnob = (convertTimeToAngle(updatedStartTime) + angleSweepedAfterMove)
                                    endingAngle = startAngleKnob
                                        .plus(convertHourToAngle(updatedStartTime, updatedEndTime))
                                        .adjustWhenLessThanZero()
                                }
                            }
                            val startTimeCalc = convertAngleToLocalDateTime(startAngleKnob)
                            var endTimeCalc = convertAngleToLocalDateTime(endingAngle)
                            updatedStartTime = startTimeCalc

                            /* val amPmEndTime = endTimeCalc.format(DateTimeFormatter.ofPattern("a"))
                             val amPmStartTime = startTimeCalc.format(DateTimeFormatter.ofPattern("a"))

                             if (amPmEndTime.equals("am", ignoreCase = true)) {
                                 if (!amPmStartTime.equals("am", ignoreCase = true)) {
                                     endTimeCalc = endTimeCalc.plusDays(1)
                                 }
                             }*/
                            updatedEndTime = endTimeCalc

                            startTimeUpdateInvoker.invoke(updatedStartTime)
                            endTimeUpdateInvoker.invoke(updatedEndTime)


                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            change.consumeAllChanges()

                        })
                }
            }, onDraw = {
            shapeCenter = center
            radius = size.minDimension / 2
            startIconAngle = convertTimeToAngle(sTime)
            endIconAngle = convertTimeToAngle(enTime)
            val sweepAngle = getSweepAngle(updatedStartTime, updatedEndTime, endIconAngle, startIconAngle)
            drawRotatingKnob(
                startIconAngle,
                knobStrokeWidth,
                sweepAngle
            )
        })

        val sweepAngle = getSweepAngle(updatedStartTime, updatedEndTime, endIconAngle, startIconAngle)

        DrawHandleLinesOnTheKnob(
            clockRadiusDp,
            knobTrackStrokeWidth,
            sweepAngle,
            startIconAngle
        )

        StartEndIcon(reduceOffsetIcon, shapeCenter, startIconAngle, radius, endIconAngle, clockSize)
    }

}

@Composable
private fun BoxScope.StartEndIcon(
    reduceOffsetIcon: Float,
    shapeCenter: Offset,
    startIconAngle: Float,
    radius: Float,
    endIconAngle: Float,
    clockSize: Dp
) {
    Box(
        Modifier
            .size(clockSize)
            .align(Alignment.Center)
    ) {
        KnobIcon(
            reduceOffsetIcon,
            shapeCenter,
            true,
            startIconAngle,
            radius,

            )
        KnobIcon(
            reduceOffsetIcon,
            shapeCenter,
            false,
            endIconAngle,
            radius,

            )
    }
}

private fun correctEndTimeNotConcerningDay(
    timeAtTouchScroll: LocalDateTime?,
    endTimeValue: LocalDateTime
) = if (timeAtTouchScroll!!.compareTo(endTimeValue) < 0) {
    endTimeValue.toInstant(TimeZone.UTC).minus(1, DateTimeUnit.DAY, TimeZone.UTC)
} else {
    endTimeValue.toInstant(TimeZone.UTC)
}

private fun getSweepAngle(
    startTimeValue: LocalDateTime,
    endTimeValue: LocalDateTime,
    endIconAngle: Float,
    startIconAngle: Float
) = if (startTimeValue.dayOfMonth < endTimeValue.dayOfMonth) {
    // adjust the sweepAngle
    endIconAngle.plus(360.minus(startIconAngle))
} else {
    endIconAngle.minus(startIconAngle)
}

fun Float.asCanvasArcStartAngle(): Float {
    return this.minus(90f)
}

private fun Float.adjustWhenLessThanZero(): Float {
    return if (this < 0) {
        360.plus(this)
    } else {
        this
    }
}

private fun Float.adjustWithin360(): Float {
    // adjust the angle
    return if (this > 360) {
        this.minus(360)
    } else {
        this
    }

}

fun Float.fixArcThreeOClock(): Float = this.plus(90f)


@Composable
private fun BoxScope.DrawHandleLinesOnTheKnob(
    clockSize: Dp,
    knobTrackStrokeWidth: Float,
    endAngle: Float,
    startAngle: Float
) {
    val handleLinesCount = endAngle.div(3).toInt()
    Box(
        modifier = Modifier
            .rotate(startAngle)
            .align(Alignment.Center)
            .size(clockSize.plus(knobTrackStrokeWidth.div(2).dp))
    ) {
        repeat(handleLinesCount) {
            if (it > 3 && it < handleLinesCount.minus(3)) {
                Handle(it, endAngle, handleLinesCount)
            }
        }
    }
}

@Composable
private fun BoxScope.DrawTicks(clockSize: Dp) {
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .size(clockSize)
    ) {
        repeat(120) {
            Tick(it)
        }
    }
}

@Composable
fun BoxScope.Handle(handle: Int, totalAngle: Float, handleLinesCount: Int) {
    val angle = totalAngle / handleLinesCount * handle

    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .width(3.dp)
            .height(14.dp)
            .rotate(angle)
            .offset(0.dp, (-150).dp)
            .background(Color(1, 0, 0).copy(alpha = 0.6f))
    )

}

@Composable
fun BoxScope.Tick(tick: Int) {
    val angle = 360f / 120f * tick
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .width(if (tick % 5 == 0) 1.5.dp else 1.dp)
            .height(if (tick % 5 == 0) 6.dp else 2.dp)
            .rotate(angle)
            .offset(0.dp, (-110).dp)
            .background(textSecondary.copy(alpha = 0.5f)),
    )

}

@Composable
private fun KnobIcon(
    reduceOffsetIcon: Float,
    shapeCenter: Offset,
    isStart: Boolean,
    angleKnob: Float,
    radius: Float
) {
    val angleKnob =
        angleKnob.minus(90f)// .minus(90f),fix startAngle - Starting angle in degrees. 0 represents 3 o'clock
    // start icon offset
    val iconX = (shapeCenter.x + cos(angleKnob.toDouble().toRadians()) * radius).toFloat()
    val iconY = (shapeCenter.y + sin(angleKnob.toDouble().toRadians()) * radius).toFloat()
    val iconOffset = Offset(iconX, iconY)

    SleepBedTimeIcon(isStart,
        Modifier
            .offset {
                IconOffset(iconOffset, reduceOffsetIcon)
            })
}

private fun IconOffset(
    startIconOffset: Offset,
    reduceOffsetIcon: Float
) = IntOffset(
    startIconOffset.x
        .toInt()
        .minus(reduceOffsetIcon / 2)
        .toInt(),
    startIconOffset.y
        .toInt()
        .minus(reduceOffsetIcon / 2)
        .toInt()
)

private fun DrawScope.drawClockCircle(clockRadius: Float, shapeCenter: Offset) {
    drawCircle(color = offGray, radius = clockRadius)
    drawClockNumerals(shapeCenter, clockRadius)
}

private fun DrawScope.drawClockNumerals(
    shapeCenter: Offset,
    clockRadius: Float
) {
    val labels = clockLabels()

    labels.forEachIndexed { index, it ->
        val paint = normalTextPaint()

        val boldPaint = boldTextPaint()
        //val rect = Rect()
        //paint.getTextBounds(it, 0, it.length, rect);
        val angle = index * PI * 2 / 24 - (PI / 2)

        val x = (shapeCenter.x + cos(angle) * clockRadius.times(0.75f) - 0f).toFloat()
        val y =
            (shapeCenter.y + sin(angle) * clockRadius.times(0.78f) + 0f).toFloat()
        if (isClockBoldNeeded(it) || it.toInt() % 2 == 0
        ) {
            drawContext.canvas.drawText(
                it,
                x,
                y,
                if (isClockBoldNeeded(it)) boldPaint else paint,
                textSize = 32f,
                PaintMode.FILL,
            )
        }

    }
}

private fun boldTextPaint(): NativePaint {
    return Paint().asFrameworkPaint().apply {
        color = Color.White.toArgb()
    }
}

private fun normalTextPaint(): NativePaint {
    return Paint().asFrameworkPaint().apply {
        color = Color.LightGray.toArgb()
    }
}

private fun isClockBoldNeeded(it: String) =
    it.contains("AM", ignoreCase = true) || it.contains(
        "PM",
        ignoreCase = true
    )

private fun clockLabels() =
    arrayOf(
        "12AM",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6AM",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12PM",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6PM",
        "7",
        "8",
        "9",
        "10",
        "11"
    )

private fun DrawScope.drawBlackKnobBackground(knobTrackStrokeWidth: Float) {
    drawCircle(color = Color.Black)
    drawArc(
        Color(1, 0, 0), 0f, 360f,
        useCenter = true, style = Stroke(width = knobTrackStrokeWidth)
    )
}

private fun DrawScope.drawRotatingKnob(
    startAngle: Float,
    knobStrokeWidth: Float,
    sweepAngleForKnob: Float
) {

    drawArc(
        color = offGray,
        startAngle = startAngle.asCanvasArcStartAngle(),
        sweepAngle = sweepAngleForKnob,
        false,
        style = Stroke(width = knobStrokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
}

@Composable
fun VerticalGroupTime(isStart: Boolean, startTime: LocalDateTime, endTime: LocalDateTime) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(top = 28.dp))

        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {

            SleepBedTimeIcon(isStart, Modifier.size(18.dp))
            Spacer(modifier = Modifier.padding(end = 6.dp))
            Text(
                text = if (isStart) "BEDTIME" else "WAKE UP",
                style = Typography.subtitle2.copy(color = textSecondary)
            )
        }
        Spacer(modifier = Modifier.padding(top = 2.dp))
        Text(
            text = if (isStart) "${startTime.hour}:${startTime.minute}" else "${endTime.hour}:${endTime.minute}",
            style = Typography.h5.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.padding(top = 2.dp))
        Text(
            text = if (isStart) "Today" else "Tomorrow",
            style = Typography.subtitle2.copy(color = textSecondary)
        )
    }
}

@Composable
private fun SleepBedTimeIcon(isStart: Boolean, modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(isStart),
        tint = textSecondary,
        contentDescription = null, modifier = modifier
    )

}

@Composable
private fun painterResource(isStart: Boolean) =
    if (isStart) PainterRes.ic_bed() else PainterRes.ic_alarm()

private fun getRotationAngleWithfixArcThreeOClock(currentPosition: Offset, center: Offset): Float {
    val theta = radians(currentPosition, center)

    var angle = theta.degreesFromRadian()

    if (angle < 0) {
        angle += 360.0
    }
    return angle.toFloat().fixArcThreeOClock()
}

fun Double.degreesFromRadian(): Double {
    return this * 180.div(PI)
}

private fun radians(
    currentPosition: Offset,
    center: Offset
): Double {
    val (dx, dy) = currentPosition - center
    return atan2(dy, dx).toDouble()
}
