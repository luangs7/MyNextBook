package com.lgdevs.mynextbook.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.lgdevs.mynextbook.designsystem.ui.components.googlebutton.GoogleButton
import com.lgdevs.mynextbook.login.R
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginFooter(
    modifier: Modifier = Modifier,
    showGoogleButton: Flow<Boolean>,
    onGoogleListener: () -> Unit,
    onSignIn: () -> Unit
) {

    val showButtonState by showGoogleButton.collectAsState(initial = false)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showButtonState) {
            GoogleButton(
                text = stringResource(id = R.string.signin_google),
                textColor = Color.Black,
                backgroundColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                onGoogleListener.invoke()
            }
        }
        Button(
            onClick = { onSignIn.invoke() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.signin),
                style = MaterialTheme.typography.body1
            )
        }
    }
}
