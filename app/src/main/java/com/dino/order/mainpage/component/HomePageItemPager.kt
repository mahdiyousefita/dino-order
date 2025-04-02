package com.dino.order.mainpage.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePageItemPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    content: @Composable (Int, Boolean) -> Unit
){

    // Track the current and previous page
    var currentPage by remember { mutableStateOf(pagerState.currentPage) }

    LaunchedEffect(pagerState.currentPage) {
        // If the page changes, update the currentPage
        currentPage = pagerState.currentPage
    }

    VerticalPager(
        state = pagerState,
        pageSpacing = (80).dp,
        contentPadding = PaddingValues(
            start = 24.dp, end = 24.dp, top = 32.dp, bottom = 32.dp
        ),
        modifier = modifier
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(42.dp))
                .background(color = Color.Transparent)
        ) {

            content(page, page == currentPage)
        }
    }
}