/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.theme.MyTheme

/**
 * Created by Kevin 2021-03-04
 */
class MainActivity : AppCompatActivity() {
    private val viewModel: TimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.valueAnim?.cancel()
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val viewModel: TimeViewModel = viewModel()
    Surface(color = MaterialTheme.colors.background) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            CompleteText(viewModel)
            ProgressCircle(viewModel)
            EditText(viewModel)
            Row {
                StartButton(viewModel)
                StopButton(viewModel)
            }
        }
    }
}

@Composable
private fun CompleteText(viewModel: TimeViewModel) {
    Text(
        text = viewModel.completeString(),
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colors.primary
    )
}

@Composable
fun ProgressCircle(viewModel: TimeViewModel) {
    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp),
            onDraw = {
                drawCircle(
                    color = Color.LightGray,
                    style = Stroke(width = 16.dp.toPx(), pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(3.dp.toPx(), 3.dp.toPx())))
                )
                drawArc(
                    startAngle = -90f,
                    sweepAngle = viewModel.progressSweepAngle(),
                    useCenter = false,
                    style = Stroke(width = 16.dp.toPx()),
                    color = Color.Cyan,
                    alpha = 0.5f
                )
            }
        )
        Text(
            text = viewModel.timeLeftValue()
        )
    }
}

@Composable
private fun EditText(viewModel: TimeViewModel) {
    if (viewModel.hideEditText()) return
    TextField(
        modifier = Modifier
            .padding(16.dp)
            .size(200.dp, 60.dp),
        value = viewModel.editTextValue(),
        onValueChange = {
            viewModel.editTextValueChanged(it)
        },
        label = { Text("Countdown Seconds") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun StartButton(viewModel: TimeViewModel) {
    Button(
        modifier = Modifier
            .width(150.dp)
            .padding(16.dp),
        enabled = viewModel.startButtonEnabled(),
        onClick = {
            viewModel.clickStartButton()
        }
    ) {
        Text(text = viewModel.startButtonDisplayString())
    }
}

@Composable
private fun StopButton(viewModel: TimeViewModel) {
    Button(
        modifier = Modifier
            .width(150.dp)
            .padding(16.dp),
        enabled = viewModel.stopButtonEnabled(),
        onClick = {
            viewModel.clickStopButton()
        }
    ) {
        Text(text = "Stop")
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
