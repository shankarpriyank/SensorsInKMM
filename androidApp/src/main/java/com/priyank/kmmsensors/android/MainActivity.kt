package com.priyank.kmmsensors.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.priyank.kmmsensors.SensorData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val gg = SensorData(this.applicationContext).giveGyroScopeData()
        lifecycleScope.launch {
            gg.collect{
                Log.e("First" ,"${it.first!!}")
                Log.e("Second" ,"${it.second}")
                Log.e("Third" ,"${it.third}")


            }
        }
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                  val pp =   gg.collectAsState(initial = Triple(1f,1f,1f))
                  ColorBlendingEffect(x = pp.value.first!!, y = pp.value.second!!, z = pp.value.third!!)

                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}
@Composable
fun ColorBlendingEffect(x: Float, y: Float, z: Float) {
   // val animatedColor = remember { Animatable(Color(x.roundToInt(), y.roundToInt(), z.roundToInt())) }

//    LaunchedEffect(key1 = x, key2 = y, key3 = z) {
//        launch {
//            animatedColor.animateTo(Color(x.roundToInt(), y.roundToInt(), z.roundToInt()), animationSpec = tween(10))
//        }
//    }

    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Magenta,
        Color.Yellow,
        Color.Cyan,
        Color.Red
    )

    val transition = updateTransition(targetState = Triple(x, y, z))

    val gradientPosition by transition.animateInt(
        transitionSpec = { tween(durationMillis = 3000, easing = LinearEasing) },
        label = ""
    ) { gyroscopeData ->
        gyroscopeData.first.roundToInt() + gyroscopeData.second.roundToInt() + gyroscopeData.third.roundToInt()
    }

    val animatedColor = remember { Animatable(colors[0]) }

    LaunchedEffect(key1 = x, key2 = y, key3 = z) {
        launch {
            colors.forEachIndexed { index, color ->
                animatedColor.animateTo(
                    targetValue = colors[(index + 1) % colors.size],
                    animationSpec = tween(durationMillis = 100, easing = LinearEasing)
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(
            color = animatedColor.value
        ).graphicsLayer(translationX = gradientPosition * 300f)
    )
}



@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        ColorBlendingEffect(x = 8f, y = 0.4f , z = 1f)
    }
}
