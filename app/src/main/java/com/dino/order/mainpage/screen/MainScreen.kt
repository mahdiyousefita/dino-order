package com.dino.order.mainpage.screen

import android.app.Activity
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.dino.order.corefeature.presentation.activity.theme.StatusBarColor
import com.dino.order.errorfeature.presentation.screen.ErrorScreen
import com.dino.order.mainpage.viewmodel.MainScreenViewModel
import com.dino.order.nointernetfeature.presentation.screen.NoInternetScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay


@Destination
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val activity = context as? Activity

    val viewModel: MainScreenViewModel = hiltViewModel()

    var webViewInstance by remember { mutableStateOf<WebView?>(null) }

    var isNoInternet by remember { mutableStateOf(!viewModel.isConnected(context)) }

    val systemUiController = rememberSystemUiController()


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
    Scaffold { innerPadding ->
        systemUiController.setStatusBarColor(StatusBarColor)

        Row(
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
        }
    }
}









