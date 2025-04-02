package com.dino.order.corefeature.presentation.coponent.pager.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.dino.order.corefeature.presentation.ui.theme.DPExtraLarge
import com.dino.order.corefeature.presentation.ui.theme.DPMedium

@Composable
fun SolidWithBackgroundImageAndDiscScreen(
    backgroundImageId: Int,
    title: String,
    disc: String,
) {

    val textColors = remember {
        mutableStateOf(Color.White)
    }

    Box {
        Image(
            painter = painterResource(id = backgroundImageId),
            contentDescription = disc,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(DPExtraLarge)
        ) {
           Text(
               color = textColors.value,
               text = title,
               fontSize = 24.sp,
               maxLines = 1

           )
            Text(
                color = textColors.value,
                text = disc,
                fontSize = 16.sp,
                maxLines = 4
            )
        }
    }
}