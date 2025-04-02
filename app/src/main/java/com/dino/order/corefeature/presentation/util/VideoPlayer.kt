package com.dino.order.corefeature.presentation.util

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import com.dino.order.R

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(url: String, isActive: Boolean) {
    val context = LocalContext.current
    var showImage by remember { mutableStateOf(url.isNullOrEmpty()) }
    var isPlaying by remember { mutableStateOf(true) }
    var isBuffering by remember { mutableStateOf(true) }


    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            if (!url.isNullOrEmpty()) {
                try {
                    setMediaItem(MediaItem.fromUri(Uri.parse(url)))
                    prepare()
                    playWhenReady = true
                } catch (e: Exception) {
                    showImage = true // Show image if loading fails
                }
            } else {
                showImage = true
            }
        }
    }



    // Listen for buffering & errors
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isBuffering = state == Player.STATE_BUFFERING
                if (state == Player.STATE_READY) isPlaying = true
                if (state == Player.STATE_ENDED) isPlaying = false
            }

            override fun onPlayerError(error: PlaybackException) {
                showImage = true // If error, show fallback image
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // Pause video when inactive
    LaunchedEffect(isActive) {
        if (isActive) {
            exoPlayer.play()
            isPlaying = true
        } else {
            exoPlayer.pause()
            isPlaying = false
        }
    }

//    DisposableEffect(Unit) {
//        onDispose { exoPlayer.release() }
//    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (showImage) {
            // Show fallback image if no video
            Image(
                painter = painterResource(id = R.drawable.ic_dino),
                contentDescription = "Fallback Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }else{
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = false // Hide default controls
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM // Crop the video
                    }
                }
            )

            // Show loading indicator when buffering
            if (isBuffering) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(bottom = 24.dp, end = 24.dp)
                            .size(24.dp),
                        color = Color.White
                    )
                }

            }

            // Play/pause button
            if (!isPlaying) {
                IconButton(
                    onClick = {
                        exoPlayer.play()
                        isPlaying = true
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp)  // Adjust button size
                        .background(color = Color.Black.copy(alpha = 0.5f), shape = CircleShape)
                ) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null, tint = Color.White)
                }
            } else {
                // Show pause button when the video is playing
                IconButton(
                    onClick = {
                        exoPlayer.pause()
                        isPlaying = false
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(164.dp)  // Adjust button size
                        .background(color = Color.Transparent, shape = CircleShape)
                ) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null, tint = Color.Transparent)
                }
            }
        }

    }
}
