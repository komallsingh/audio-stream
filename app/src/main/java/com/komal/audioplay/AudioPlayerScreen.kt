package com.komal.audioplay

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun AudioPlayerScreen() {
    var isPlaying by remember { mutableStateOf(false) }
    val context = LocalContext.current

    //initialising the media player
    var mediaPlayer = remember {
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
    //UI
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
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
            Slider(
                value = mediaPlayer.currentPosition.toFloat(),
                onValueChange = {
                    mediaPlayer.seekTo(it.toInt())
                },
                valueRange = 0f..duration.toFloat()
            )
        }
    }
}