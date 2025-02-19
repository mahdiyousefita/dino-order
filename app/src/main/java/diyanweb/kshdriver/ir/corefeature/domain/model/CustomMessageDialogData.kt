package diyanweb.kshdriver.ir.corefeature.domain.model

import android.os.Parcelable
import diyanweb.kshdriver.ir.corefeature.presentation.util.Resource
import kotlinx.parcelize.Parcelize


/**
 * The `CustomMessageDialogData` class represents the data for a custom message dialog.
 *
 * @param messages The list of custom message dialog messages.
 * @param type The type of the custom message dialog.
 */
@Parcelize
class CustomMessageDialogData private constructor(
    val messages: List<CustomMessageDialogContentItem>, val type: String
) : Parcelable {

    /**
     * The `CustomMessageDialogBuilder` class provides a builder pattern to create `CustomMessageDialogData` instances.
     */
    class CustomMessageDialogBuilder {
        private var messages: MutableList<CustomMessageDialogContentItem> = mutableListOf()
        private var type: String = CustomMessageDialogType.Empty.type

        /**
         * Adds a custom message dialog message with optional string resource IDs and string values.
         *
         * @param title The title string value.
         * @param description The description string value.
         * @return The `CustomMessageDialogBuilder` instance for method chaining.
         */
        fun addMessage(
            title: StringResourceContent,
            description: StringResourceContent
        ): CustomMessageDialogBuilder = addMessage(
            title = title,
            descriptions = listOf(description)
        )

        /**
         * Adds a custom message dialog message with optional string resource IDs and string values.
         *
         * @param title The title string value.
         * @param descriptions The list of description string values
         * @return The `CustomMessageDialogBuilder` instance for method chaining.
         */
        fun addMessage(
            title: StringResourceContent,
            descriptions: List<StringResourceContent>
        ): CustomMessageDialogBuilder {
            messages.add(
                CustomMessageDialogContentItem(
                    title = title,
                    descriptions = descriptions.toMutableList()
                )
            )
            return this
        }

        /**
         * Adds a custom message dialog message with a resource error.
         *
         * @param title The title string value.
         * @param resourceError The resource error containing the message resource ID and message.
         * @return The `CustomMessageDialogBuilder` instance for method chaining.
         */
        fun <T> addMessage(
            title: StringResourceContent,
            resourceError: Resource.Error<T>
        ): CustomMessageDialogBuilder = addMessage(
            title = title,
            descriptions = listOf(resourceError.message)
        )

        /**
         * Sets the type of the custom message dialog.
         *
         * @param value The custom message dialog type.
         * @return The `CustomMessageDialogBuilder` instance for method chaining.
         */
        fun setType(value: CustomMessageDialogType): CustomMessageDialogBuilder {
            type = value.type
            return this
        }

        /**
         * Checks if the message list is not empty.
         *
         * @return `true` if the message list is not empty, `false` otherwise.
         */
        fun isMessageListNotEmpty(): Boolean = messages.isNotEmpty()

        /**
         * Builds and returns the `CustomMessageDialogData` instance.
         *
         * @return The built `CustomMessageDialogData` instance.
         */
        fun build() = CustomMessageDialogData(
            messages = messages.toSet().toMutableList(), type = type
        )
    }
}