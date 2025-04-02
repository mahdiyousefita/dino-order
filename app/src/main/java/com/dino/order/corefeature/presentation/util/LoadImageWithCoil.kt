package com.dino.order.corefeature.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation


@Composable
fun LoadImageWithCoil(imageUrl: String, modifier: Modifier) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build()
    )

    val state = painter.state

    Box(modifier = modifier) {
        if (state is coil.compose.AsyncImagePainter.State.Loading) {
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

        Image(
            painter = painter,
            contentDescription = "Loaded Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}