package diyanweb.kshdriver.ir.mainpage.viewmodel

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.net.http.SslError
import android.webkit.CookieManager
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import diyanweb.kshdriver.ir.DiyanWebApplication
import diyanweb.kshdriver.ir.corefeature.presentation.viewmodel.HaveUIEvent
import diyanweb.kshdriver.ir.corefeature.presentation.viewmodel.HaveUIEventImpl
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    @ApplicationContext app: Context,

    )  : AndroidViewModel(app as DiyanWebApplication),
    HaveUIEvent by HaveUIEventImpl(app as DiyanWebApplication) {



    var isError = mutableStateOf(false)

    val appUrl = "https://app.diyan.ir/"


    private val context: Context = app.applicationContext




    // Function to create WebView instance
    fun createWebView(): WebView {
        return WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            settings.allowContentAccess = true
            settings.setSupportMultipleWindows(true)
            settings.setJavaScriptCanOpenWindowsAutomatically(true)
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.databaseEnabled = true
            settings.setGeolocationEnabled(true)
            settings.mediaPlaybackRequiresUserGesture = false
            settings.allowFileAccess = true
            settings.textZoom = 100
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true

            webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    val url = request?.url.toString()

                    return when {
                        url.startsWith("tel:") -> {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                            context.startActivity(intent) // Open the phone app
                            true // Tell WebView not to load the URL
                        }

                        url.startsWith("mailto:") -> {
                            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                            context.startActivity(intent) // Open email app
                            true
                        }

                        url.startsWith("intent:") -> {
                            try {
                                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                                context.startActivity(intent)
                                true
                            } catch (e: Exception) {
                                false
                            }
                        }

                        else -> false // Let WebView load the URL normally
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    isError.value = false  // Reset error flag on successful load
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    if (error != null && error.description.toString()
                            .contains("ERR_NAME_NOT_RESOLVED")
                    ) {
                        isError.value = true // Set isError to true to show the error screen
                    } else if (request?.isForMainFrame == true) {
                        isError.value = true
                    }
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    if (request?.isForMainFrame == true) {
                        isError.value = true
                    }
                }

                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    isError.value = true
                }
            }

            loadUrl(appUrl)
        }
    }


    // Check Internet Connection
    fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // Function to save cookies
    fun saveCookies(context: Context, appUrl: String) {
        val cookieManager = CookieManager.getInstance()
        val cookies = cookieManager.getCookie(appUrl)
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("cookies", cookies).apply()
    }

    // Function to load cookies
    fun loadCookies(context: Context, webView: WebView, appUrl: String) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val cookies = sharedPreferences.getString("cookies", null)
        cookies?.let {
            val cookieManager = CookieManager.getInstance()
            cookieManager.setCookie(appUrl, it)
        }
    }


}