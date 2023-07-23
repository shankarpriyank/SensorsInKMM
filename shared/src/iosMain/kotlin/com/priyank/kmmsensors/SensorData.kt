package com.priyank.kmmsensors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

actual class SensorData  {

    private val sensorData: MutableStateFlow<Triple<Float?,Float?,Float?>> = MutableStateFlow(Triple(1f,1f,1f))


    actual fun giveGyroScopeData(): Flow<Triple<Float?, Float?, Float?>> {
        return sensorData

    }


}