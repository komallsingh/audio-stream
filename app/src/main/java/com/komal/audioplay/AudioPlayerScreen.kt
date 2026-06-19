package com.komal.audioplay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AudioPlayerScreen(){
    var isPlaying by mutableStateOf(false)
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
       Box(
           Modifier.fillMaxSize(),
           contentAlignment = Alignment.Center
       ){
           Button(
               onClick = {
                   isPlaying = !isPlaying
               }
           ){
               Text(text = if (isPlaying) "Pause" else "Play")
           }
       }
    }
    }