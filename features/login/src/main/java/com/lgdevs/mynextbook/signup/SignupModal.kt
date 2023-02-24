package com.lgdevs.mynextbook.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import com.lgdevs.mynextbook.designsystem.ui.components.dialog.DefaultDialog
import com.lgdevs.mynextbook.designsystem.ui.components.dialog.DialogArguments
import com.lgdevs.mynextbook.login.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupModal(
    onDismiss: () -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(true) }

    val arguments = DialogArguments(
        title = stringResource(id = R.string.signup_title),
        text = stringResource(id = R.string.signup_description),
        dismissText = stringResource(id = R.string.confirmation_button)
    )

    DefaultDialog(
        arguments = arguments,
        isDialogOpen = isDialogOpen,
        onDismissRequest = {
            isDialogOpen = false
            onDismiss()
        }
    )
}
