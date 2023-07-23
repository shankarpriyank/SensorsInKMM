package com.priyank.kmmsensors

import androidx.compose.ui.window.ComposeUIViewController
val sensorData = SensorData()

fun MainViewController() = ComposeUIViewController { App(sensorData) }


