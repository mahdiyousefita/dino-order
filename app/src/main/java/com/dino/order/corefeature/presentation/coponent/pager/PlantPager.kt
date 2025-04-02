package com.dino.order.corefeature.presentation.coponent.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.dino.order.R
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlantPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        pageSpacing = (-100).dp,
        contentPadding = PaddingValues(start = 54.dp, end = 54.dp, top = 30.dp),
        modifier = modifier,
    ) { page ->
        Box(
            Modifier
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    val rotationAngle = if (page < pagerState.currentPage) {
                        lerp(start = -10f, stop = 0f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
                    } else {
                        lerp(start = 10f, stop = 0f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
                    }

                    rotationZ = rotationAngle

                    val scale = lerp(
                        start = 0.70f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    scaleX = scale
                    scaleY = scale
                }
                .zIndex(if (page == pagerState.currentPage) 1f else 0f)
                .size(325.dp, 144.dp)
                .blur(
                    radius = if (page == pagerState.currentPage) 0.dp else 5.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(42.dp))
                    .background(color = MaterialTheme.colorScheme.onBackground)
            ) {
                content(page)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    PlantPager(
        pagerState = pagerState
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}