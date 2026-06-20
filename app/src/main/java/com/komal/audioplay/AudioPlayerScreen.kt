package com.komal.audioplay

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun AudioPlayerScreen() {
    var isPlaying by remember { mutableStateOf(false) }
    val context = LocalContext.current

    //initialising the media player
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.song)
    }
    //duration
    val duration=mediaPlayer.duration
    //releasing the resource
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
    val currentPosition by produceState(initialValue = 0) {
        while (true) {
            value=mediaPlayer.currentPosition
            delay(500)
        }
    }

    // slider position
    var sliderPosition by remember { mutableStateOf(0f) }
    LaunchedEffect(currentPosition) {
        sliderPosition = currentPosition.toFloat()
    }
    //UI
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition=it
            },
            onValueChangeFinished = {
                mediaPlayer.seekTo(sliderPosition.toInt())
            },
            valueRange = 0f..duration.toFloat()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IconButton(onClick = {
                mediaPlayer.seekTo(0)
            }) {
                Icon(
                    imageVector = Icons.Filled.SkipPrevious,
                    contentDescription = "Previous"
                )
            }
            if (isPlaying) {
                IconButton(onClick = {
                    isPlaying = false
                    mediaPlayer.pause()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Pause,
                        contentDescription = "Pause"
                    )
                }
            } else {
                IconButton(onClick = {
                    isPlaying = true
                    mediaPlayer.start()
                }) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play"
                    )
                }
            }

            IconButton(
                onClick = {
                    mediaPlayer.seekTo(mediaPlayer.duration)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.SkipNext,
                    contentDescription = "Next"
                )
            }
        }
    }
}