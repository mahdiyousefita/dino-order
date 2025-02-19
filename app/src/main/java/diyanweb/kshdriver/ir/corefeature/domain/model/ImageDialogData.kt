package diyanweb.kshdriver.ir.corefeature.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

/**
 * The `ImageDialogData` data class represents the data associated with an image dialog.
 * It contains information such as the title string resource ID, image resource ID or URI string,
 * image URL, and the type of image resource.
 *
 * @param titleStrRes The string resource ID for the title of the image dialog.
 * @param resId The drawable resource ID for the image. Default value is -1.
 * @param uriString The URI string representing the image. Default value is null.
 * @param url The URL representing the image. Default value is null.
 * @param type The type of image resource, indicating whether it is based on resource ID, URI, or URL.
 */
@Parcelize
data class ImageDialogData(
    @StringRes val titleStrRes: Int = 0,
    @DrawableRes val resId: Int = -1,
    val uriString: String? = null,
    val url: String? = null,
    val type: ImageDialogImageResourceType = ImageDialogImageResourceType.ResId
) : Parcelable