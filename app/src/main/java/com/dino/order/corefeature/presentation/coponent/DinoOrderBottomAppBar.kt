package com.dino.order.corefeature.presentation.coponent

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dino.order.R
import com.dino.order.corefeature.presentation.ui.theme.DPExtraSmall
import com.dino.order.corefeature.presentation.ui.theme.DPMedium
import com.dino.order.corefeature.presentation.ui.theme.DPSmall
import com.dino.order.corefeature.presentation.util.MenuItem
import com.dino.order.corefeature.presentation.util.PopBackStackLevel
import com.dino.order.corefeature.presentation.util.UIEvent
import com.dino.order.destinations.MainScreenDestination
import com.dino.order.destinations.ProfileScreenDestination
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun DinoOrderBottomAppBar(
    mutableUIEvent: MutableSharedFlow<UIEvent>,
    @StringRes currentItemLabelRes: Int,
) {

    val scope = rememberCoroutineScope()


    val bottomAppBarItemRes = remember {
        listOf(
            MenuItem(
                R.drawable.round_home_24,
                R.string.home
            ) {
                mutableUIEvent.emit(
                    UIEvent.NavigateWithPopBackStack(
                        direction = MainScreenDestination,
                        level = PopBackStackLevel.ALL
                    )
                )
            },

            MenuItem(
                R.drawable.round_person_24,
                R.string.profile
            ) {
                mutableUIEvent.emit(UIEvent.Navigate(ProfileScreenDestination))
            },
        )
    }

    BottomAppBar {
        bottomAppBarItemRes.forEach { bottomAppBarItem ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(MaterialTheme.shapes.extraLarge)
                    .clickable(
                        onClick = {
                            if (bottomAppBarItem.labelStringResId != currentItemLabelRes) {
                                scope.launch {
                                    bottomAppBarItem.onClick()
                                }
                            }

                        }
                    )
                    .padding(DPSmall)
            ) {
                Icon(
                    painter = painterResource(id = bottomAppBarItem.iconResId),
                    contentDescription = stringResource(
                        id = bottomAppBarItem.labelStringResId
                    )
                )

                Spacer(modifier = Modifier.size(DPMedium))

                if (bottomAppBarItem.labelStringResId == currentItemLabelRes)
                    Card(
                        shape = CircleShape,
                        modifier = Modifier
                            .width(35.dp)
                            .height(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {

                    }
            }
        }
    }
}