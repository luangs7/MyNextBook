package com.lgdevs.mynextbook.login.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lgdevs.mynextbook.designsystem.ui.components.textinput.TextInputField
import com.lgdevs.mynextbook.designsystem.ui.components.textinput.TextInputState
import com.lgdevs.mynextbook.designsystem.ui.theme.linked
import com.lgdevs.mynextbook.login.R
import com.lgdevs.mynextbook.signup.SignupModal

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginDataContent(
    emailState: MutableState<TextInputState>,
    passwordState: MutableState<TextInputState>,
    modifier: Modifier = Modifier,
) {
    var requestDialog by remember { mutableStateOf(false) }

    if (requestDialog) {
        SignupModal {
            requestDialog = false
        }
    }

    Column(
        modifier = modifier,
    ) {
        TextInputField(
            label = stringResource(id = R.string.email_field),
            fieldState = emailState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
        ) {
            emailState.value = TextInputState.Default(TextFieldValue(it.text))
        }
        TextInputField(
            label = stringResource(id = R.string.password_field),
            fieldState = passwordState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
        ) {
            passwordState.value = TextInputState.Default(TextFieldValue(it.text))
        }
        Text(
            text = stringResource(id = R.string.signup),
            style = linked,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, end = 16.dp)
                .clickable {
                    requestDialog = true
                },
        )
    }
}
