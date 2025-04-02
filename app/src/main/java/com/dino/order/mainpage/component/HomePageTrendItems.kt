package com.dino.order.mainpage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dino.order.R
import com.dino.order.corefeature.presentation.util.LoadImageWithCoil
import com.dino.order.corefeature.presentation.util.Product
import com.dino.order.corefeature.presentation.util.VideoPlayer

@Composable
fun HomePageTrendItems(
    product: Product,
    isActive: Boolean,

){

    val backgroundColor= remember {
        mutableStateOf(Color.Black)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor.value),
    ) {
        if (product.url != null) {
            backgroundColor.value = Color.Black
            if (!product.isImage){
                VideoPlayer(
                    url = product.url,
                    isActive = isActive
                )
            } else {
                LoadImageWithCoil(imageUrl = product.url, modifier = Modifier.fillMaxSize())
            }
        } else {
            backgroundColor.value = Color.Transparent
            Icon(
                painter = painterResource(id = R.drawable.ic_dino),
                contentDescription = "null",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 44.dp),
                tint = Color.Unspecified
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = product.name,
                color = if (backgroundColor.value == Color.Transparent)Color.Black else Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = product.description,
                color = if (backgroundColor.value == Color.Transparent)Color.Black else Color.White,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray.copy(alpha = 0.5f)
                    ),
                    onClick = { /* Buy */ }) {
                    Text(stringResource(R.string.buy_now), color = Color.White)
                }
            }

        }
    }
}
