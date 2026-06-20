package com.komal.audioplay

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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


@Composable
fun AudioPlayerScreen() {
    var isPlaying by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val mediaPlayer = remember { //initialising the media player
        MediaPlayer.create(context, R.raw.song)
    }
    val duration = mediaPlayer.duration
    val currentPosition by produceState(initialValue = 0) {
        while (true) {
            if (mediaPlayer.isPlaying) {
                value = mediaPlayer.currentPosition
            }
            delay(500)
        }
    }
    var sliderPosition by remember { mutableStateOf(0f) }
    DisposableEffect(Unit) {
        mediaPlayer.setOnCompletionListener {
            isPlaying = false
        }
        onDispose {
            mediaPlayer.release()
        }
    }
    LaunchedEffect(currentPosition) {
        sliderPosition = currentPosition.toFloat()
    }


    //--> UI
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hanuman-Chalisa.mp3")
        Text("Duration = ${mediaPlayer.duration}")
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
            },
            onValueChangeFinished = {
                mediaPlayer.seekTo(sliderPosition.toInt())
            },
            valueRange = 0f..duration.toFloat()
        )
        Row(
            modifier=Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(text = formatTime(currentPosition))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = formatTime(duration))
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
fun formatTime(ms: Int): String {
    val totalsec= ms/1000
    val min=totalsec/60
    val sec=totalsec%60

    return "%02d:%02d".format(min,sec)
}