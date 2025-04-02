package com.dino.order.profilefeature.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dino.order.R
import com.dino.order.corefeature.presentation.coponent.DinoOrderBottomAppBar
import com.dino.order.profilefeature.presentation.viewmodel.ProfileScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination


@Destination
@Composable
fun ProfileScreen(){

    val viewModel: ProfileScreenViewModel = hiltViewModel()

    Scaffold(
        bottomBar = {
            DinoOrderBottomAppBar(
                mutableUIEvent = viewModel.mutableUIEventFlow,
                currentItemLabelRes = R.string.profile
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Text(text = "Profile screen")
        }
    }
}