package diyanweb.kshdriver.ir.mainpage.screen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.view.View
import android.webkit.CookieManager
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import diyanweb.kshdriver.ir.corefeature.presentation.activity.theme.StatusBarColor
import diyanweb.kshdriver.ir.errorfeature.presentation.screen.ErrorScreen
import diyanweb.kshdriver.ir.mainpage.viewmodel.MainScreenViewModel
import diyanweb.kshdriver.ir.nointernetfeature.presentation.screen.NoInternetScreen
import diyanweb.kshdriver.ir.webviewfeature.presentation.screen.WebViewScreen
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


    // Enable WebView debugging
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        WebView.setWebContentsDebuggingEnabled(true)
    }


    // Enable cookies
    val cookieManager = CookieManager.getInstance()
    cookieManager.setAcceptCookie(true)
    webViewInstance?.let { cookieManager.setAcceptThirdPartyCookies(it, true) }

    // Load cookies from persistent storage
    webViewInstance?.let { viewModel.loadCookies(context, it, viewModel.appUrl) }

    // Swipe refresh state
    var isSwipeRefreshEnabled by remember { mutableStateOf(true) }

    webViewInstance?.webChromeClient = object : WebChromeClient() {
        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
            super.onShowCustomView(view, callback)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }
    }

    // Handle back press
    BackHandler {
        webViewInstance?.let {
            if (it.canGoBack()) {
                it.goBack()
            } else {
                activity?.finish()
            }
        }
    }

    // Save cookies when the activity is paused
    DisposableEffect(Unit) {
        onDispose {
            viewModel.saveCookies(context, viewModel.appUrl) // Save cookies when leaving the screen
        }
    }

    // Monitor network status & recreate WebView when needed
    LaunchedEffect(Unit) {
        while (true) {
            isNoInternet = !viewModel.isConnected(context)
            delay(2000) // Check every 2 seconds
        }
    }

    // Initialize WebView on first launch
    LaunchedEffect(Unit) {
        if (webViewInstance == null && viewModel.isConnected(context)) {
            isNoInternet = false
            webViewInstance = viewModel.createWebView()
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
                        webViewInstance = viewModel.createWebView()
                    }
                }

                viewModel.isError.value -> ErrorScreen {
                    webViewInstance = viewModel.createWebView() // Recreate WebView on retry
                }

                webViewInstance != null -> WebViewScreen(
                    webViewInstance!!,
                    isSwipeRefreshEnabled,
                    0.5f
                )
            }
        }
    }
}









