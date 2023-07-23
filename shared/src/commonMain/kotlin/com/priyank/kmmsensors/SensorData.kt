package com.priyank.kmmsensors

import kotlinx.coroutines.flow.Flow

expect class SensorData{
      fun giveGyroScopeData(): Flow<Triple<Float?, Float?, Float?>>
}