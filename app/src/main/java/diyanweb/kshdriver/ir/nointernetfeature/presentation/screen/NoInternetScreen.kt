package diyanweb.kshdriver.ir.nointernetfeature.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diyanweb.kshdriver.ir.R
import diyanweb.kshdriver.ir.corefeature.presentation.ui.theme.DPLarge
import diyanweb.kshdriver.ir.corefeature.presentation.ui.theme.DPMedium
import diyanweb.kshdriver.ir.corefeature.presentation.ui.theme.DPSmall
import diyanweb.kshdriver.ir.errorfeature.presentation.screen.ErrorScreen

// No Internet Screen
@Composable
fun NoInternetScreen(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // error icon
        Box(
            modifier = Modifier
                .background(Color(0xFFFBF0F0), shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wifi),
                contentDescription = null,
                tint = Color(0xFF3083F7),
                modifier = Modifier
                    .padding(30.dp)
                    .size(80.dp)
            )
        }

        Spacer(modifier = Modifier.size(34.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 34.dp),
            text = stringResource(id = R.string.no_internet_connection),
            fontWeight = FontWeight.Medium,
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 34.dp),
            text = stringResource(id = R.string.no_internet_connection_dsc),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color(0xFF4A5463)
        )

        Spacer(modifier = Modifier.size(34.dp))


        Text(
            text = stringResource(id = R.string.troubleshooting),
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.size(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 76.dp)
        ) {
            TroubleshootStepsText(dsc = stringResource(id = R.string.troubleshooting_step_one))
            TroubleshootStepsText(dsc = stringResource(id = R.string.troubleshooting_step_two))
            TroubleshootStepsText(dsc = stringResource(id = R.string.troubleshooting_step_three))

        }

        Spacer(modifier = Modifier.size(34.dp))


        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryContainer else Color.Black
            ),
            shape = RoundedCornerShape(DPMedium)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
private fun TroubleshootStepsText(dsc: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_success),
            contentDescription = null,
            tint = Color(0xFF4EC15C)
        )
        Spacer(modifier = Modifier.size(DPSmall))
        Text(
            text = dsc,
            color = Color(0xFF4A5463)

        )
    }
    Spacer(modifier = Modifier.size(DPLarge))
}

@Preview(showSystemUi = true)
@Composable
private fun NoInternetScreenPre() {
    NoInternetScreen {

    }
}