package diyanweb.kshdriver.ir.corefeature.data.spref

import android.content.Context
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import diyanweb.kshdriver.ir.corefeature.domain.model.AppLanguage
import diyanweb.kshdriver.ir.corefeature.domain.model.AppTheme
import kotlinx.coroutines.flow.map


/**
 * The `DataStoreUtil` class provides utility methods for working with DataStore in Android applications.
 *
 * @param context The `Context` object used for accessing resources.
 */
class DataStoreUtil @Inject constructor(private val context: Context) {
    companion object {
        // Define the DataStore with the specified name
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

        // Define keys for accessing specific preferences
        val DARK_THEME_SETTINGS_KEY = stringPreferencesKey("DARK_THEME_SETTINGS")
        val LANGUAGE_KEY = stringPreferencesKey("LANGUAGE")
    }

    /**
     * Retrieves the theme preference from the DataStore as a flow of `AppTheme`.
     *
     * @return A `Flow` emitting the current theme preference.
     */
    fun getTheme(): Flow<AppTheme> = context.dataStore.data.map { preferences ->
        AppTheme.valueOf(
            preferences[DARK_THEME_SETTINGS_KEY] ?: AppTheme.SystemSettings.name
        )
    }

    /**
     * Saves the theme preference to the DataStore.
     *
     * @param darkThemeEnabled The value representing the dark theme preference.
     */
    suspend fun saveTheme(darkThemeEnabled: String) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME_SETTINGS_KEY] = darkThemeEnabled
        }
    }

    /**
     * Retrieves the language preference from the DataStore as a flow of `AppLanguage`.
     *
     * @return A `Flow` emitting the current language preference.
     */
    fun getLanguage(): Flow<AppLanguage> = context.dataStore.data.map { preferences ->
        AppLanguage.valueOf(
            preferences[LANGUAGE_KEY] ?: AppLanguage.Farsi.name
        )
    }

    /**
     * Saves the language preference to the DataStore.
     *
     * @param appLanguage The `AppLanguage` representing the language preference.
     */
    suspend fun saveLanguage(appLanguage: AppLanguage) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = appLanguage.name
        }
    }
}