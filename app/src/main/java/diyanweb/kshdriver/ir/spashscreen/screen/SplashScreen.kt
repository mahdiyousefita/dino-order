package diyanweb.kshdriver.ir.spashscreen.screen

import android.content.Intent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import diyanweb.kshdriver.ir.MainActivity
import diyanweb.kshdriver.ir.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val context = LocalContext.current

    // Delay the navigation to the next screen after a certain time
    // This part is just for caution and actually navigation to next screen done in viewModel!
    LaunchedEffect(key1 = true) {
        delay(3_000L + 200)
        val intent = Intent(context, MainActivity::class.java)

        // Navigate to the main page and pop the splash screen from the back stack
        navController.navigate("main_page") {
            // Pop up to the splash screen, effectively removing it
            popUpTo("splash_screen") {
                inclusive = true // This removes the splash screen from the back stack
            }
            launchSingleTop = true
        }
    }
    // Create an infinite animation transition for the app logo
    val infiniteTransition = rememberInfiniteTransition()
    val animationCoefficient by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            TweenSpec(durationMillis = 3_000L.toInt()),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Display the splash screen with the app logo
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Icon(
            painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(180.dp)
                .scale(animationCoefficient),
            tint = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
    }
}