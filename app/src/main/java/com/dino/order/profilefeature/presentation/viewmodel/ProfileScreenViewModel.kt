package com.dino.order.profilefeature.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.dino.order.DinoOrderApplication
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEvent
import com.dino.order.corefeature.presentation.viewmodel.HaveUIEventImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    @ApplicationContext app: Context,

    ): AndroidViewModel(app as DinoOrderApplication),
    HaveUIEvent by HaveUIEventImpl(app as DinoOrderApplication)  {
}