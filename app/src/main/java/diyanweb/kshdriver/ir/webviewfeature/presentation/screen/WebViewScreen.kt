package diyanweb.kshdriver.ir.webviewfeature.presentation.screen

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import diyanweb.kshdriver.ir.mainpage.component.CustomSwipeRefreshLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(webView: WebView, isSwipeRefreshEnabled: Boolean, swipeSensitivity: Float) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            CustomSwipeRefreshLayout(context).apply {
                // Set swipe sensitivity if required
                this.swipeSensitivity = swipeSensitivity
                setOnRefreshListener {
                    refreshScope.launch {
                        refreshing = true
                        webView.reload()
                        delay(800)
                        refreshing = false
                    }
                }

                // Check if the WebView already has a parent, and if so, remove it
                val parent = webView.parent
                if (parent != null) {
                    (parent as? ViewGroup)?.removeView(webView)
                }

                // Add the WebView to the CustomSwipeRefreshLayout
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