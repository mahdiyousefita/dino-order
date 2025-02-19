package diyanweb.kshdriver.ir.corefeature.domain.model

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import diyanweb.kshdriver.ir.R
import kotlinx.parcelize.Parcelize


@Parcelize
sealed class StringResourceContent : Parcelable {
    data class Str(val data: String) : StringResourceContent()
    data class StringResId(@StringRes val data: Int) : StringResourceContent()

    fun asString(context: Context): String {
        return when (this) {
            is Str -> this.data
            is StringResId -> context.getString(this.data)
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is Str -> this.data
            is StringResId -> stringResource(id = this.data)
        }
    }
}

fun Int.asStringResourceContent() = StringResourceContent.StringResId(this)
fun String.asStringResourceContent() = StringResourceContent.Str(this)

@Composable
fun List<StringResourceContent>.asString(
    separator: String = " " + stringResource(id = R.string.and) + " "
) = this.map { it.asString() }.joinToString(separator = separator)
