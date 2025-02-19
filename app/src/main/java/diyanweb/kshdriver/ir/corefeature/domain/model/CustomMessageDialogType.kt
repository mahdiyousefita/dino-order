package diyanweb.kshdriver.ir.corefeature.domain.model

/**
 * The `CustomMessageDialogType` sealed class represents the types of custom message dialogs.
 *
 * @param type The string representation of the dialog type.
 */
sealed class CustomMessageDialogType(val type: String) {
    /**
     * Represents an empty dialog type.
     */
    object Empty : CustomMessageDialogType("Empty")

    /**
     * Represents an error dialog type.
     */
    object Error : CustomMessageDialogType("Error")

    /**
     * Represents a success dialog type.
     */
    object Success : CustomMessageDialogType("Success")
}