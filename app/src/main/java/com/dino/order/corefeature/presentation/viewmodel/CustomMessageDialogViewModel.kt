package com.dino.order.corefeature.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import com.dino.order.DinoOrderApplication
import com.dino.order.R
import com.dino.order.corefeature.domain.model.CustomMessageDialogData
import com.dino.order.corefeature.domain.model.CustomMessageDialogType
import javax.inject.Inject


/**
 * ViewModel class for the custom message dialog.
 * Extends [AndroidViewModel] to have access to the application context.
 * Implements [HaveUIEvent] interface to handle UI events.
 *
 * @param app The application context.
 * @param savedStateHandle The saved state handle.
 */
@HiltViewModel
class CustomMessageDialogViewModel @Inject constructor(
    @ApplicationContext app: Context,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(app as DinoOrderApplication),
    HaveUIEvent by HaveUIEventImpl(app as DinoOrderApplication) {

    // Stores the custom message dialog data obtained from the saved state handle.
    val customMessageDialogData: CustomMessageDialogData =
        savedStateHandle.get<CustomMessageDialogData>("customMessageDialogData")!!

    // Determines the header text based on the custom message dialog type.
    val header = when (customMessageDialogData.type) {
        CustomMessageDialogType.Error.type -> R.string.error
        CustomMessageDialogType.Success.type -> R.string.successful
        else -> R.string.empty
    }

    /**
     * Navigates up in the navigation hierarchy.
     */
    fun navigateUp() {
        navigateUp(viewModelScope)
    }
}