package diyanweb.kshdriver.ir.corefeature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The `CustomMessageDialogMessage` data class represents a custom message dialog message.
 *
 * @param title The title of the dialog message.
 * @param descriptions The descriptions of the dialog message.
 */
@Parcelize
data class CustomMessageDialogContentItem(
    var title: StringResourceContent,
    val descriptions: MutableList<StringResourceContent>,
) : Parcelable