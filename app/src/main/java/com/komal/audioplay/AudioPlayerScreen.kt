package com.komal.audioplay

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun AudioPlayerScreen(){
    var isPlaying by remember{mutableStateOf(false)}
    val context= LocalContext.current
    var mediaPlayer= remember {
        MediaPlayer.create(context,R.raw.song)
    }
    DisposableEffect(Unit){
        onDispose{
            mediaPlayer.release()
        }
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
       Box(
           Modifier.fillMaxSize(),
           contentAlignment = Alignment.Center
       ){
           if(isPlaying){
               IconButton(onClick = {
                   isPlaying=false
                   mediaPlayer.pause()
               }) {
                   Icon(
                       imageVector = Icons.Filled.Pause,
                       contentDescription = "Pause"
                   )
               }
               }else{
                   IconButton(onClick = {
                       isPlaying=true
                       mediaPlayer.start()
                   }) {
                       Icon(
                           imageVector = Icons.Filled.PlayArrow,
                           contentDescription = "Play"
                       )
                   }
               }
           }
           }
       }