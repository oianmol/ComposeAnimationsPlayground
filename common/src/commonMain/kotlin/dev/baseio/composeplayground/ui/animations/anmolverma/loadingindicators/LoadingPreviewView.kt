package dev.baseio.composeplayground.ui.animations.anmolverma.loadingindicators

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.baseio.composeplayground.ui.theme.Typography

/**
 *  Inspired by https://github.com/SwiftfulThinking/SwiftfulLoadingIndicators/blob/main/Sources/SwiftfulLoadingIndicators/LoadingPreviewView.swift
 */
@Composable
fun LoadingPreviewView(modifier: Modifier) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        LoadingAnimation.values().forEach { loadingAnim ->
            Column(
                Modifier.padding(12.dp)
            ) {
                Text(
                    text = loadingAnim.toString(),
                    style = Typography.h6.copy(fontWeight = FontWeight.Bold)
                )
                Column {
                    Text(
                        text = "Sizes",
                        style = Typography.subtitle1.copy(fontWeight = FontWeight.Bold, color = Color.Black)
                    )
                    Size.values().forEach { size ->
                        Column(
                            Modifier
                                .border(1.dp, Color.Black)
                                .padding(8.dp)
                        ) {
                            LoadingIndicator(loadingAnim, size.factor, Speed.normal.factor)
                            Text(size.toString(), style = Typography.caption)
                        }
                    }
                }
                Divider(Modifier.padding(vertical = 12.dp))
                Text(
                    text = "Speeds",
                    style = Typography.subtitle1.copy(fontWeight = FontWeight.Bold, color = Color.Black)
                )
                Speed.values().forEach { speed ->
                    Column(
                        Modifier
                            .border(1.dp, Color.Black)
                            .padding(8.dp)
                    ) {
                        LoadingIndicator(loadingAnim, Size.medium.factor, speed.factor)
                        Text(speed.toString(), style = Typography.caption.copy(color = Color.Black))
                    }
                }
            }
        }
    }


}