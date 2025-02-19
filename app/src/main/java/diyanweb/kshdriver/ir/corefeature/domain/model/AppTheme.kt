package diyanweb.kshdriver.ir.corefeature.domain.model

import androidx.annotation.StringRes
import diyanweb.kshdriver.ir.R


/**
 * The `AppTheme` enum class represents the different themes available in the application.
 *
 * @param strRes The string resource ID representing the localized name of the theme.
 */
enum class AppTheme(@StringRes val strRes: Int) {
    /**
     * Represents the dark theme.
     */
    DarkTheme(R.string.dark_theme),

    /**
     * Represents the light theme.
     */
    LightTheme(R.string.light_theme),

    /**
     * Represents the theme based on system settings.
     */
    SystemSettings(R.string.system_settings)
}

/**
 * Converts the `AppTheme` enum value to a boolean representing the theme selection.
 *
 * @param systemSettings A flag that shows which theme is selected in system settings.
 * @return The boolean value representing the theme selection.
 */
fun AppTheme.toBoolean(systemSettings: Boolean) = when (this) {
    AppTheme.SystemSettings -> systemSettings
    AppTheme.LightTheme -> false
    AppTheme.DarkTheme -> true
}