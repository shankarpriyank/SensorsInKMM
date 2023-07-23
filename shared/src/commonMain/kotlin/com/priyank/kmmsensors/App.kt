package com.priyank.kmmsensors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun App( sensorData: SensorData){

  val data =    sensorData.giveGyroScopeData().collectAsState(initial = Triple(1f, 1f, 1f))

    ShowDetails(data.value.first.checkIfNull(),data.value.second.checkIfNull(), data.value.third.checkIfNull() )


}

@Composable
fun ShowDetails(x: Float, y: Float, z: Float) {

    Column(verticalArrangement = Arrangement.Center,) {
        Text(modifier = Modifier.padding(vertical = 20.dp),text = "Acceleration force along the x axis (including gravity) $x", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(modifier = Modifier.padding(vertical = 20.dp),text = "Acceleration force along the y axis (including gravity)$y", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(modifier = Modifier.padding(vertical = 20.dp),text = "Acceleration force along the z axis (including gravity) $z", fontWeight = FontWeight.Bold, fontSize = 20.sp)


    }



}
fun Float?.checkIfNull(): Float {
    return this ?: 0.0f
}