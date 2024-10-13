package diyanweb.kshdriver.ir

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import diyanweb.kshdriver.ir.mainpage.screen.MainScreen
import diyanweb.kshdriver.ir.spashscreen.screen.SplashScreen
import diyanweb.kshdriver.ir.ui.theme.Diyan_Web_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Diyan_Web_AppTheme {
                Scaffold { innerpading ->
                    Box(
                        modifier = Modifier
                            .padding(innerpading)
                            .imePadding()
                    ) {
                        StartApplication { webViewInstance ->
                            webView = webViewInstance // Save the WebView instance
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (this::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed() // Exit the app if no history
        }
    }

}
@Composable
fun StartApplication(onWebViewReady: (WebView) -> Unit) {
    val navController = rememberNavController()
    val start = "splash_screen"

    NavHost(navController = navController, startDestination = start) {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        composable("main_page") {
            MainScreen(navController = navController, onWebViewReady = onWebViewReady)
        }
    }
}