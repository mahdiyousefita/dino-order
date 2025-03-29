package com.dino.order.corefeature.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dino.order.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.dino.order.corefeature.domain.model.CustomMessageDialogData
import com.dino.order.corefeature.domain.model.CustomMessageDialogType
import com.dino.order.corefeature.domain.model.asString
import com.dino.order.corefeature.presentation.activity.theme.ButtonPrimary
import com.dino.order.corefeature.presentation.ui.theme.DPExtraLarge
import com.dino.order.corefeature.presentation.ui.theme.DPLarge
import com.dino.order.corefeature.presentation.ui.theme.DPMedium
import com.dino.order.corefeature.presentation.ui.theme.DPSmall
import com.dino.order.corefeature.presentation.viewmodel.CustomMessageDialogViewModel


/**
 * Composable function for displaying a custom message dialog.
 *
 * The function defines a composable dialog UI using Jetpack Compose. It takes in a [customMessageDialogData]
 * parameter, which represents the data for the custom message dialog. The custom message dialog displays a header
 * icon, a header text, a list of messages, and an "OK" button for dismissing the dialog.
 *
 * @param customMessageDialogData The data for the custom message dialog.
 */
@Destination(style = DestinationStyle.Dialog::class)
@Composable
@Suppress("UNUSED_PARAMETER")
fun CustomMessageDialogScreen(customMessageDialogData: CustomMessageDialogData) {
    // Create an instance of the CustomMessageDialogViewModel using Hilt
    val viewModel: CustomMessageDialogViewModel = hiltViewModel()

    // Determine the context color based on the custom message dialog type
    val contextColor = when (viewModel.customMessageDialogData.type) {
        CustomMessageDialogType.Error.type -> MaterialTheme.colorScheme.onErrorContainer
        else -> MaterialTheme.colorScheme.onBackground
    }

    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .background(
                when (viewModel.customMessageDialogData.type) {
                    CustomMessageDialogType.Error.type -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.background
                }
            )
            .padding(DPSmall)
    ) {
        // Header Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header Icon
            Icon(
                painter = painterResource(
                    id = when (viewModel.customMessageDialogData.type) {
                        CustomMessageDialogType.Error.type -> R.drawable.ic_error
                        CustomMessageDialogType.Success.type -> R.drawable.ic_success
                        else -> R.drawable.ic_success
                    }
                ),
                contentDescription = stringResource(R.string.dialog_type_icon),
                tint = contextColor,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(DPMedium))

            // Header Text
            Text(
                text = stringResource(id = viewModel.header),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(end = DPLarge),
                color = contextColor
            )
        }

        // List of Messages
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = DPExtraLarge)
        ) {
            items(viewModel.customMessageDialogData.messages.size) { message ->
                // Message Title
                Text(
                    text = viewModel.customMessageDialogData.messages[message].title.asString(),
                    color = contextColor,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                // Message Descriptions
                Text(
                    text = viewModel.customMessageDialogData.messages[message].descriptions.asString(),
                    color = (contextColor).copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(DPLarge))
            }

            // "OK" Button
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(end = DPExtraLarge)
                ) {
                    Button(
                        onClick = viewModel::navigateUp,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonPrimary)
                    ) {
                        Text(stringResource(R.string.ok))
                    }
                }
            }
        }
    }
}