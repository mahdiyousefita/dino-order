package com.dino.order.corefeature.presentation.viewmodel

import com.ramcosta.composedestinations.spec.Direction
import com.dino.order.DinoOrderApplication
import com.dino.order.corefeature.domain.model.CustomMessageDialogData
import com.dino.order.corefeature.domain.model.ImageDialogData
import com.dino.order.corefeature.presentation.util.PopBackStackLevel
import com.dino.order.corefeature.presentation.util.UIEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


/**
 * Implementation of the [HaveUIEvent] interface that emits UI events.
 *
 * @param app The [AniBaranKSHDriverApplication] instance used to access the shared UI event flows.
 */
class HaveUIEventImpl(
    app: DinoOrderApplication
) : HaveUIEvent {
    /**
     * A mutable shared flow that emits UI events.
     * It is initialized with the [mutableUIEventFlow] property of the provided [app].
     */
    override val mutableUIEventFlow: MutableSharedFlow<UIEvent> = app.mutableUIEventFlow

    /**
     * A shared flow that emits UI events.
     * It provides an immutable view of the UI events for external observation.
     * It is initialized with the [uIEventFlow] property of the provided [app].
     */
    override val uIEventFlow = app.uIEventFlow

    /**
     * Navigates to a destination specified by the given direction and pops back to a specified level in the back stack.
     * It emits a [UIEvent.NavigateWithPopBackStack] event with the provided [direction] and [level].
     *
     * @param direction The direction specifying the destination to navigate to.
     * @param scope The coroutine scope in which the event emission occurs.
     * @param level The level specifying the number of destinations to pop from the back stack.
     * @return A [Job] representing the navigation job.
     */
    override fun navigateWithPopBackStackToDestination(
        direction: Direction,
        scope: CoroutineScope,
        level: PopBackStackLevel
    ) = scope.launch {
        mutableUIEventFlow.emit(
            UIEvent.NavigateWithPopBackStack(
                direction = direction, level = level
            )
        )
    }

    /**
     * Navigates to a destination specified by the given direction.
     * It emits a [UIEvent.Navigate] event with the provided [direction].
     *
     * @param direction The direction specifying the destination to navigate to.
     * @param scope The coroutine scope in which the event emission occurs.
     * @return A [Job] representing the navigation job.
     */
    override fun navigateToDestination(
        direction: Direction,
        scope: CoroutineScope
    ) = scope.launch {
        mutableUIEventFlow.emit(UIEvent.Navigate(direction))
    }

    /**
     * Navigates up to the parent destination.
     * It emits a [UIEvent.NavigateUp] event.
     *
     * @param scope The coroutine scope in which the event emission occurs.
     * @return A [Job] representing the navigation job.
     */
    override fun navigateUp(scope: CoroutineScope) = scope.launch {
        mutableUIEventFlow.emit(UIEvent.NavigateUp)
    }

    /**
     * Shows a message in a dialog using the provided [customMessageDialogData].
     * It emits a [UIEvent.ShowCustomMessageDialog] event with the provided [customMessageDialogData].
     *
     * @param customMessageDialogData The data specifying the custom message dialog to show.
     * @param scope The coroutine scope in which the event emission occurs.
     * @return A [Job] representing the dialog job.
     */
    override fun showCustomMessageDialog(
        customMessageDialogData: CustomMessageDialogData,
        scope: CoroutineScope
    ) = scope.launch {
        mutableUIEventFlow.emit(UIEvent.ShowCustomMessageDialog(customMessageDialogData))
    }

    /**
     * Shows an image in a dialog using the provided [imageDialogData].
     * It emits a [UIEvent.ShowImageDialog] event with the provided [imageDialogData].
     *
     * @param imageDialogData The data specifying the image dialog to show.
     * @param scope The coroutine scope in which the event emission occurs.
     * @return A [Job] representing the dialog job.
     */
    override fun showImageInDialog(
        imageDialogData: ImageDialogData,
        scope: CoroutineScope
    ) = scope.launch {
        mutableUIEventFlow.emit(UIEvent.ShowImageDialog(imageDialogData))
    }
}