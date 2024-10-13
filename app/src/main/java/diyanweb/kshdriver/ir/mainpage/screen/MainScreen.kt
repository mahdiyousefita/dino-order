package diyanweb.kshdriver.ir.mainpage.screen

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun MainScreen(navController: NavController, onWebViewReady: (WebView) -> Unit) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    val appurl  = "https://app.diyan.ir/"

    webView.setBackgroundColor(0xfffff)

    // Enable WebView debugging
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        WebView.setWebContentsDebuggingEnabled(true)
    }

    // Configure WebView settings
    with(webView.settings) {
        javaScriptEnabled = true
        domStorageEnabled = true
        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        allowContentAccess = true
        setSupportMultipleWindows(true)
        setJavaScriptCanOpenWindowsAutomatically(true)
        cacheMode = WebSettings.LOAD_DEFAULT
        setDatabaseEnabled(true)
        setGeolocationEnabled(true)
        setMediaPlaybackRequiresUserGesture(false)
        setAllowFileAccess(true)
        textZoom = 100
        setUseWideViewPort(true) // Enable wide viewport
        loadWithOverviewMode = true // Load in overview mode
    }

    // Enable cookies
    val cookieManager = CookieManager.getInstance()
    cookieManager.setAcceptCookie(true)
    cookieManager.setAcceptThirdPartyCookies(webView, true)

    // Load cookies from persistent storage
    loadCookies(context, webView, appurl)

//    webView.webViewClient = object : WebViewClient() {
//        override fun shouldOverrideUrlLoading(
//            view: WebView?,
//            request: WebResourceRequest?
//        ): Boolean {
//            view?.loadUrl(request?.url.toString())
//            return true
//        }
//    }
    webView.webChromeClient = object : WebChromeClient() {
        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
            // Handle full-screen content if needed
            super.onShowCustomView(view, callback)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            // Optionally handle progress updates
            super.onProgressChanged(view, newProgress)
        }
    }

    // Load the initial URL
    LaunchedEffect(Unit) {
        webView.loadUrl(appurl)
    }

    // Notify MainActivity that WebView is ready
    onWebViewReady(webView)

//        LazyRow(
//            Modifier.fillMaxSize().background(Color.Black)
//        ) {
//            item {
//                Card(modifier = Modifier.fillMaxWidth().background(Color.Black)){
//                    WebViewScreen(webView = webView)
//                }
//            }
//        }

    val scrollState = rememberScrollState()
    Row(
        Modifier
            .fillMaxSize()
    ) {
        WebViewScreen(webView)
    }

    // Save cookies when the activity is paused
    DisposableEffect(Unit) {
        onDispose {
            saveCookies(context, appurl)
        }
    }
}

// Function to save cookies
fun saveCookies(context: Context, appurl: String) {
    val cookieManager = CookieManager.getInstance()
    val cookies = cookieManager.getCookie(appurl)
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("cookies", cookies).apply()
}

// Function to load cookies
fun loadCookies(context: Context, webView: WebView, appurl: String) {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val cookies = sharedPreferences.getString("cookies", null)
    if (cookies != null) {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setCookie(appurl, cookies)
    }
}


@Composable
fun WebView() {
    // Declare a string that contains a URL
    val mUrl = "https://app.diyan.ir/"

    // Adding a WebView inside AndroidView with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            this.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            this.webChromeClient = CustomWebChromeClient()
            this.webViewClient = CustomWebViewClient() // Set the custom WebViewClient

            with(settings) {
                javaScriptEnabled = true
                domStorageEnabled = true
                mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                allowContentAccess = true
                setSupportMultipleWindows(true)
                setJavaScriptCanOpenWindowsAutomatically(true)
                cacheMode = android.webkit.WebSettings.LOAD_DEFAULT
                setDatabaseEnabled(true)
                setGeolocationEnabled(true)
                setMediaPlaybackRequiresUserGesture(false)
                setAllowFileAccess(true)
                textZoom = 100
                setUseWideViewPort(true) // Enable wide viewport
                loadWithOverviewMode = true // Load in overview mode
            }
        }
    }, update = {
        it.loadUrl(mUrl)
    })
}

class CustomWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        // Load the URL within the WebView
        view?.loadUrl(request?.url.toString())
        return true // Indicate that you handled the event
    }
}

class CustomWebChromeClient : WebChromeClient() {
    override fun onCloseWindow(window: WebView?) {}

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        // Handle console messages if needed
        return super.onConsoleMessage(consoleMessage)
    }
}

@Composable
fun WebViewScreen(webView: WebView) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            SwipeRefreshLayout(context).apply {
                setOnRefreshListener {
                    refreshScope.launch {
                        refreshing = true
                        webView.reload()
                        delay(800)
                        refreshing = false
                    }
                }
                addView(
                    webView.apply {
                        this.layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                )
            }
        },
        update = { view ->
            view.isRefreshing = refreshing
        }
    )
}


