package diyanweb.kshdriver.ir

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import diyanweb.kshdriver.ir.corefeature.presentation.util.UIEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@HiltAndroidApp
class DiyanWebApplication: Application() {

    /**
     * A mutable shared flow used for emitting UI events.
     */
    val mutableUIEventFlow: MutableSharedFlow<UIEvent> = MutableSharedFlow()

    /**
     * A shared flow representing UI events.
     */
    val uIEventFlow: SharedFlow<UIEvent> = mutableUIEventFlow

    override fun onCreate() {
        super.onCreate()
    }
}