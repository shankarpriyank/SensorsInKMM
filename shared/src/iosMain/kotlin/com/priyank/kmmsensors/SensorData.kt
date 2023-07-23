package com.priyank.kmmsensors


import kotlinx.cinterop.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import platform.CoreMotion.CMMotionManager

actual class SensorData {


    private val sensorData: MutableStateFlow<Triple<Float?, Float?, Float?>> =
        MutableStateFlow(Triple(1f, 1f, 1f))
    val manager = CMMotionManager().apply {
        deviceMotionUpdateInterval = 20 / 1000.0
    }


    actual fun giveGyroScopeData(): Flow<Triple<Float?, Float?, Float?>> {
        manager.startGyroUpdates()
        manager.startAccelerometerUpdates()

        emitData()
        return sensorData

    }

    private fun emitData() {
        GlobalScope.launch {
            while (true) {
                delay(100)
                val acceleration = manager.accelerometerData?.acceleration
                var xAcceleration: Double? = 0.0
                var yAcceleration: Double? = 0.0
                var zAcceleration: Double? = 0.0
                memScoped {
                    val nativePtr = acceleration?.getPointer(this)
                    val cMAcceleration = nativePtr?.pointed
                    xAcceleration = cMAcceleration?.x
                    yAcceleration = cMAcceleration?.y
                    zAcceleration = cMAcceleration?.z
                }


                sensorData.emit(
                    Triple(
                        xAcceleration?.toFloat(),
                        yAcceleration?.toFloat(),
                        zAcceleration?.toFloat(),
                    )
                )
            }
        }

    }
}



