package com.lgdevs.splitfeature

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.lgdevs.mynextbook.designsystem.ui.components.dialog.DefaultDialog
import com.lgdevs.mynextbook.designsystem.ui.components.dialog.DialogArguments

@Composable
fun DownloadFeature(
    featureName: String,
    manager: SplitInstallManager,
    onDismiss: () -> Unit,
    setState: (SplitState) -> Unit
) {
    var isDialogOpen by rememberSaveable { mutableStateOf(true) }
    DisposableEffect(featureName) {
        val request = SplitInstallRequest.newBuilder()
            .addModule(featureName)
            .build()


        val listener = SplitInstallStateUpdatedListener {

            when (it.status()) {
                SplitInstallSessionStatus.PENDING -> isDialogOpen = true
                SplitInstallSessionStatus.INSTALLED -> {
                    isDialogOpen = false
                    setState(SplitState.FeatureReady)
                    onDismiss()
                }
                else -> {}
            }
        }

        manager.registerListener(listener)
        manager.startInstall(request)

        onDispose { manager.unregisterListener(listener) }
    }

    if (isDialogOpen) {
        DownloadDialog()
    }
}

@Composable
private fun DownloadDialog() {
    AlertDialog(
        onDismissRequest = { /* Does not close */ },
        title = { Text(text = stringResource(id = R.string.downloading_title)) },
        text = {
            Column {
                Text(text = stringResource(id = R.string.downloading_description))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(64.dp))
                }
            }
        },
        confirmButton = { /* Shows nothing */ },
        dismissButton = { /* Shows nothing */ }
    )
}

@Composable
fun RequestDownload(setState: (SplitState) -> Unit, onDismiss: () -> Unit) {
    var isDialogOpen by rememberSaveable { mutableStateOf(true) }

    val arguments = DialogArguments(
        title = stringResource(id = R.string.confirmation_install_title),
        text = stringResource(id = R.string.confirmation_install_description),
        confirmText = stringResource(id = R.string.confirmation_install_accept),
        dismissText = stringResource(id = R.string.confirmation_install_deny),
        onConfirmAction = { setState(SplitState.Downloading) }
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