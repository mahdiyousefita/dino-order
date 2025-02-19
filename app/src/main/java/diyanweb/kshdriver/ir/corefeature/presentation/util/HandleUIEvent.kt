package diyanweb.kshdriver.ir.corefeature.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import diyanweb.kshdriver.ir.DiyanWebApplication
import kotlinx.coroutines.flow.collectLatest


/**
 * Composable function that handles UI events by observing the UI event flow and performing corresponding navigation actions.
 *
 * @param app The application instance of type [Application].
 * @param navController The [NavController] used for navigation.
 */
@Composable
fun HandleUIEvent(
    app: DiyanWebApplication,
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        app.uIEventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.NavigateWithPopBackStack -> {
                    navController.navigate(event.direction) {
                        val popBackStackLevel = when (event.level) {
//                            is PopBackStackLevel.ALL -> navController.backQueue.size
                            is PopBackStackLevel.ALL -> navController.backQueue.size
                            is PopBackStackLevel.Number -> event.level.number
                        }
                        for (index in 0 until popBackStackLevel) {
                            navController.popBackStack()
                        }
                    }
                }
                is UIEvent.Navigate -> {
                    navController.navigate(event.direction)
                }
                is UIEvent.NavigateUp -> {
                    navController.navigateUp()
                }
                is UIEvent.ShowCustomMessageDialog -> {
//                    navController.navigate(CustomMessageDialogScreenDestination(event.data))
                }
                is UIEvent.ShowImageDialog -> {
//                    navController.navigate(ImageDialogScreenDestination(event.data))
                }
            }
        }
    }
}