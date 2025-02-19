package diyanweb.kshdriver.ir.corefeature.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import diyanweb.kshdriver.ir.DiyanWebApplication
import diyanweb.kshdriver.ir.corefeature.data.spref.DataStoreUtil
import diyanweb.kshdriver.ir.corefeature.domain.model.AppLanguage
import diyanweb.kshdriver.ir.corefeature.domain.model.toBoolean
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.flow.map


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val dataStoreUtil: DataStoreUtil,
    @ApplicationContext private val app: Context

) : AndroidViewModel(app as DiyanWebApplication),
    HaveUIEvent by HaveUIEventImpl(app as DiyanWebApplication){

    /**
     * Flow representing the current app language obtained from the data store.
     */
    val appLanguage: Flow<AppLanguage> = dataStoreUtil.getLanguage()

    /**
     * Retrieves the system theme based on the given `systemSettings` flag.
     *
     * @param systemSettings A flag indicating whether the system settings should be used.
     * @return A [Flow] emitting the theme value.
     */
    fun getSystemTheme(systemSettings: Boolean): Flow<Boolean> =
        dataStoreUtil.getTheme().map { it.toBoolean(systemSettings) }
}