package diyanweb.kshdriver.ir.corefeature.presentation.util

import com.ramcosta.composedestinations.spec.Direction
import diyanweb.kshdriver.ir.corefeature.domain.model.CustomMessageDialogData
import  diyanweb.kshdriver.ir.corefeature.domain.model.ImageDialogData

/**
 * Sealed class representing UI events.
 */
sealed class UIEvent {
    /**
     * UI event for navigating to a destination with popping back stack.
     *
     * @property direction The direction to navigate.
     * @property level The level of back stack to pop.
     */
    data class NavigateWithPopBackStack(
        val direction: Direction,
        val level: PopBackStackLevel
    ) : UIEvent()

    /**
     * UI event for navigating to a destination.
     *
     * @property direction The direction to navigate.
     */
    data class Navigate(val direction: Direction) : UIEvent()

    /**
     * UI event for navigating up.
     */
    object NavigateUp : UIEvent()

    /**
     * UI event for showing a custom message dialog.
     *
     * @property data The data for the custom message dialog.
     */
    data class ShowCustomMessageDialog(val data: CustomMessageDialogData) : UIEvent()

    /**
     * UI event for showing an image dialog.
     *
     * @property data The data for the image dialog.
     */
    data class ShowImageDialog(val data: ImageDialogData) : UIEvent()
}