package com.dino.order.corefeature.presentation.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import com.ramcosta.composedestinations.spec.Direction
import com.dino.order.corefeature.domain.model.CustomMessageDialogData
import com.dino.order.corefeature.domain.model.ImageDialogData
import com.dino.order.corefeature.presentation.util.PopBackStackLevel
import com.dino.order.corefeature.presentation.util.UIEvent


/**
 * Interface that represents an entity capable of emitting UI events.
 */
interface HaveUIEvent {
    /**
     * A mutable shared flow that emits UI events.
     * It can be used to send UI events from the implementing class to observers.
     */
    val mutableUIEventFlow: MutableSharedFlow<UIEvent>

    /**
     * A shared flow that emits UI events.
     * It provides an immutable view of the UI events for external observation.
     */
    val uIEventFlow: SharedFlow<UIEvent>

    /**
     * Navigates to a destination specified by the given direction.
     * It creates a new navigation job within the provided [scope].
     *
     * @param direction The direction specifying the destination to navigate to.
     * @param scope The coroutine scope in which the navigation job is created.
     * @return A [Job] representing the navigation job.
     */
    fun navigateToDestination(
        direction: Direction,
        scope: CoroutineScope
    ): Job

    /**
     * Navigates to a destination specified by the given direction and pops back to a specified level in the back stack.
     * It creates a new navigation job within the provided [scope].
     *
     * @param direction The direction specifying the destination to navigate to.
     * @param scope The coroutine scope in which the navigation job is created.
     * @param level The level specifying the number of destinations to pop from the back stack.
     * @return A [Job] representing the navigation job.
     */
    fun navigateWithPopBackStackToDestination(
        direction: Direction,
        scope: CoroutineScope,
        level: PopBackStackLevel
    ): Job

    /**
     * Navigates up to the parent destination.
     * It creates a new navigation job within the provided [scope].
     *
     * @param scope The coroutine scope in which the navigation job is created.
     * @return A [Job] representing the navigation job.
     */
    fun navigateUp(scope: CoroutineScope): Job

    /**
     * Shows a message in a dialog using the provided [customMessageDialogData].
     * It creates a new dialog job within the provided [scope].
     *
     * @param customMessageDialogData The data specifying the custom message dialog to show.
     * @param scope The coroutine scope in which the dialog job is created.
     * @return A [Job] representing the dialog job.
     */
    fun showCustomMessageDialog(
        customMessageDialogData: CustomMessageDialogData,
        scope: CoroutineScope
    ): Job

    /**
     * Shows an image in a dialog using the provided [imageDialogData].
     * It creates a new dialog job within the provided [scope].
     *
     * @param imageDialogData The data specifying the image dialog to show.
     * @param scope The coroutine scope in which the dialog job is created.
     * @return A [Job] representing the dialog job.
     */
    fun showImageInDialog(
        imageDialogData: ImageDialogData,
        scope: CoroutineScope
    ): Job
}