package com.dino.order.corefeature.presentation.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import com.dino.order.DinoOrderApplication
import com.dino.order.NavGraphs
import com.dino.order.corefeature.domain.model.AppLanguage
import com.dino.order.corefeature.domain.model.toLayoutDirection
import com.dino.order.corefeature.domain.model.toLocale
import com.dino.order.corefeature.presentation.activity.theme.Diyan_Web_AppTheme
import com.dino.order.corefeature.presentation.util.HandleUIEvent
import com.dino.order.corefeature.presentation.util.LocaleUtils
import com.dino.order.corefeature.presentation.viewmodel.MainActivityViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @ApplicationContext
    lateinit var app: Context

    private val viewModel: MainActivityViewModel by viewModels()


    private var navController: NavController? = null

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Determine the system theme based on the current configuration
        val systemTheme =
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> true
                Configuration.UI_MODE_NIGHT_NO -> false
                else -> false
            }

        enableEdgeToEdge()
        setContent {

            // Retrieve the app theme and app language from the view model
            val theme = viewModel.getSystemTheme(systemTheme).collectAsState(initial = systemTheme)
            val appLanguage = viewModel.appLanguage.collectAsState(initial = AppLanguage.English)

            // Create a navigation controller
            navController = rememberNavController()

            // Set the app's locale based on the selected language
            LocaleUtils.setLocale(LocalContext.current, appLanguage.value.toLocale())

            // Handle UI events and update the app's state
            HandleUIEvent(app as DinoOrderApplication, navController!!)

            Diyan_Web_AppTheme(darkTheme = theme.value) {
                CompositionLocalProvider(
                    LocalLayoutDirection provides appLanguage.value.toLayoutDirection()
                ) {
                    // Set the app's navigation graph and navigation controller
                    DestinationsNavHost(navGraph = NavGraphs.root, navController = navController as NavHostController)

                    // Handle the deep link
                }
//                Scaffold { innerpading ->
//                    Box(
//                        modifier = Modifier
//                            .padding(innerpading)
//                            .imePadding()
//                    ) {
//                        StartApplication { webViewInstance ->
//                            webView = webViewInstance // Save the WebView instance
//                        }
//                    }
//                }
            }
        }
    }

//    override fun onBackPressed() {
//        if (this::webView.isInitialized && webView.canGoBack()) {
//            webView.goBack()
//        } else {
//            super.onBackPressed() // Exit the app if no history
//        }
//    }

}
//@Composable
//fun StartApplication(onWebViewReady: (WebView) -> Unit) {
//    val navController = rememberNavController()
//    val start = "splash_screen"
//
//    NavHost(navController = navController, startDestination = start) {
//        composable("splash_screen") {
//            SplashScreen(navController = navController)
//        }
//        composable("main_page") {
//            MainScreen(navController = navController, onWebViewReady = onWebViewReady)
//        }
//    }
//}