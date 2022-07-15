package com.lgdevs.mynextbook.designsystem.ui.components.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DefaultDialog(
    arguments: DialogArguments,
    isDialogOpen: Boolean,
    onDismissRequest: () -> Unit
) {
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = arguments.title) },
            text = { Text(text = arguments.text) },
            confirmButton = {
                arguments.onConfirmAction?.let {
                    Button(onClick = it) {
                        Text(text = arguments.confirmText.orEmpty(), color = Color.White)
                    }
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDismissRequest) {
                    Text(text = arguments.dismissText, color = Color.White)
                }
            }
        )
    }
}

data class DialogArguments(
    val title: String,
    val text: String,
    val confirmText: String? = null,
    val dismissText: String,
    val onConfirmAction: (() -> Unit)? = null
)
