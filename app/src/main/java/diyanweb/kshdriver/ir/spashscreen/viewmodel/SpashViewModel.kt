package diyanweb.kshdriver.ir.spashscreen.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import diyanweb.kshdriver.ir.DiyanWebApplication
import diyanweb.kshdriver.ir.corefeature.presentation.util.PopBackStackLevel
import diyanweb.kshdriver.ir.corefeature.presentation.viewmodel.HaveUIEvent
import diyanweb.kshdriver.ir.corefeature.presentation.viewmodel.HaveUIEventImpl
import diyanweb.kshdriver.ir.destinations.MainScreenDestination
import javax.inject.Inject

@HiltViewModel
class SpashViewModel @Inject constructor(
    @ApplicationContext app: Context,
    ) : AndroidViewModel(app as Application),
HaveUIEvent by HaveUIEventImpl(app as DiyanWebApplication) {


    fun navigateToNextScreen() {
        navigateWithPopBackStackToDestination(
            direction = MainScreenDestination,
            scope = viewModelScope,
            level = PopBackStackLevel.ALL
        )
    }
}