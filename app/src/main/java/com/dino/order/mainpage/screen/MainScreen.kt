package com.dino.order.mainpage.screen

import android.app.Activity
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dino.order.R
import com.dino.order.corefeature.presentation.coponent.DinoOrderBottomAppBar
import com.dino.order.corefeature.presentation.coponent.pager.PlantPager
import com.dino.order.corefeature.presentation.coponent.pager.pages.PageWithTimer
import com.dino.order.corefeature.presentation.coponent.pager.pages.SolidWithBackgroundImageAndDiscScreen
import com.dino.order.corefeature.presentation.util.PageType
import com.dino.order.corefeature.presentation.util.Product
import com.dino.order.errorfeature.presentation.screen.ErrorScreen
import com.dino.order.mainpage.component.HomePageItemPager
import com.dino.order.mainpage.component.HomePageTrendItems
import com.dino.order.mainpage.viewmodel.MainScreenViewModel
import com.dino.order.nointernetfeature.presentation.screen.NoInternetScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val viewModel: MainScreenViewModel = hiltViewModel()
    var isNoInternet by remember { mutableStateOf(!viewModel.isConnected(context)) }

    // Swipe refresh state
    var isSwipeRefreshEnabled by remember { mutableStateOf(true) }


    // Handle back press
    BackHandler {
        // todo
    }


    // Monitor network status
    LaunchedEffect(Unit) {
        while (true) {
            isNoInternet = !viewModel.isConnected(context)
            delay(2000) // Check every 2 seconds
        }
    }

    isNoInternet = !viewModel.isConnected(context)
    Scaffold(
        bottomBar = {
            DinoOrderBottomAppBar(
                mutableUIEvent = viewModel.mutableUIEventFlow,
                currentItemLabelRes = R.string.home
            )
        }
    ) { innerPadding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isNoInternet -> NoInternetScreen {
                    if (viewModel.isConnected(context)) {
                        isNoInternet = false
                    }
                }

                viewModel.isError.value -> ErrorScreen {
                }

            }

            // todo: fake pages
            val pages = listOf(
                PageType.SolidWithBackgroundImageAndDiscScreen(
                    backgroundImageId = R.drawable.images,
                    disc = "How you doiiin",
                    title = stringResource(R.string.hellooo)
                ),
                PageType.PageWithTimer(
                    backgroundImageId = R.drawable.darkteal,
                    disc = "im just testing this shit!",
                    title = stringResource(id = R.string.and),
                    time = "10:20:32"
                ),
                PageType.SolidWithBackgroundImageAndDiscScreen(
                    backgroundImageId = R.drawable.tttt,
                    disc = "yup it is just for testing3",
                    title = stringResource(id = R.string.error)
                ),
                PageType.PageWithTimer(
                    backgroundImageId = R.drawable.idk,
                    disc = "Collection 10% off",
                    title = "Discount for watches",
                    time = "08:34:52"
                ),
            )

            val pagerState = rememberPagerState(pageCount = { pages.size })
            PlantPager(
                pagerState = pagerState
            ){ page ->
                when (val pageType = pages[page]) {
                    is PageType.SolidWithBackgroundImageAndDiscScreen -> SolidWithBackgroundImageAndDiscScreen(
                        backgroundImageId = pageType.backgroundImageId,
                        disc = pageType.disc,
                        title = pageType.title
                    )
                    is PageType.PageWithTimer -> PageWithTimer(
                        backgroundImageId = pageType.backgroundImageId,
                        disc = pageType.disc,
                        title = pageType.title,
                        time = pageType.time
                    )
                }
            }

            // todo: fake data
            val productList = listOf(
                Product("Smart Watch", "Off just for today", "https://caspian20.cdn.asset.aparat.com/aparat-video/e78daba00a331235fbadac11bd044d3163910510-1080p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjFmYTA5ZWMxNjQ3NGMxZjJmMTYyODlkYzg0ZWUzNDdjIiwiZXhwIjoxNzQzNTYzNTAzLCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.GZlP5yzOf0pvj0c6HePufy4e1lYB2Bt2ErEgliC5vHY",
                    isImage = false),
                Product("Gamin laptop", "Bes Gaming laptop in the world!", "https://www.trustedreviews.com/wp-content/uploads/sites/54/2022/01/Alienware-m17-R5-Ryzen-edition-920x614.jpg", isImage = true),
                Product("Sport shoes", "New Collection", isImage = false),
            )
            val homePageItemPagerState = rememberPagerState(pageCount = {productList.size})

            HomePageItemPager(pagerState = homePageItemPagerState) { page, isActive ->
                HomePageTrendItems(product = productList[page], isActive = isActive)
            }
        }
    }
}









