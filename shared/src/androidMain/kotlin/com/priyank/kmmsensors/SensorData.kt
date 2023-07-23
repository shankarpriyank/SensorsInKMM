package com.priyank.kmmsensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

actual class SensorData(private val context: Context) : SensorEventListener {

    private val sensorData: MutableStateFlow<Triple<Float?, Float?, Float?>> =
        MutableStateFlow(Triple(1f, 1f, 1f))

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    actual fun giveGyroScopeData(): Flow<Triple<Float?, Float?, Float?>> {
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL)
        return sensorData

    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onSensorChanged(event: SensorEvent?) {

        GlobalScope.launch {

            sensorData.emit(
                Triple(
                    event?.values?.get(0),
                    event?.values?.get(1),
                    event?.values?.get(2)
                )
            )
        }

    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}