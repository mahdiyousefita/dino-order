package diyanweb.kshdriver.ir.corefeature.presentation.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

/**
 * Utility object for managing the locale of the application.
 */
object LocaleUtils {
    /**
     * Sets the locale of the application to the specified language.
     *
     * @param context The application context.
     * @param language The language code to set.
     */
    fun setLocale(context: Context, language: String) = updateResources(context, language)

    /**
     * Updates the application resources with the specified language.
     *
     * @param context The application context.
     * @param language The language code to set.
     */
    private fun updateResources(context: Context, language: String) {
        context.resources.apply {
            val locale = Locale(language)
            val config = Configuration(configuration)

            context.createConfigurationContext(configuration)
            Locale.setDefault(locale)
            config.setLocale(locale)
            this.updateConfiguration(config, displayMetrics)
        }
    }
}