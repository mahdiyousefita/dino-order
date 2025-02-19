package diyanweb.kshdriver.ir.corefeature.domain.model

import androidx.annotation.StringRes
import androidx.compose.ui.unit.LayoutDirection
import diyanweb.kshdriver.ir.R


/**
 * The `AppLanguage` enum class represents the different languages available in the application.
 *
 * @param strRes The string resource ID representing the localized name of the language.
 */
enum class AppLanguage(@StringRes val strRes: Int) {
    /**
     * Represents the English language.
     */
    English(R.string.english),

    /**
     * Represents the Farsi language.
     */
    Farsi(R.string.farsi)
}

/**
 * Converts the `AppLanguage` enum value to its corresponding locale string.
 *
 * @return The locale string representation of the language.
 */
fun AppLanguage.toLocale(): String {
    return when (this) {
        AppLanguage.English -> "en"
        AppLanguage.Farsi -> "fa"
    }
}

/**
 * Converts a locale string to its corresponding `AppLanguage` enum value.
 *
 * @return The `AppLanguage` enum value corresponding to the locale string.
 */
fun String.toAppLanguage(): AppLanguage {
    return when (this) {
        "fa" -> AppLanguage.Farsi
        else -> AppLanguage.English
    }
}

/**
 * Converts the `AppLanguage` enum value to its corresponding layout direction.
 *
 * @return The `LayoutDirection` representing the layout direction of the language.
 */
fun AppLanguage.toLayoutDirection(): LayoutDirection {
    return when (this) {
        AppLanguage.Farsi -> LayoutDirection.Rtl
        AppLanguage.English -> LayoutDirection.Ltr
    }
}