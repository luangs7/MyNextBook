package com.lgdevs.splitfeature

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.lgdevs.mynextbook.designsystem.ui.components.dialog.DefaultDialog
import com.lgdevs.mynextbook.designsystem.ui.components.dialog.DialogArguments

@Composable
fun LoadFeature(
    context: Context,
    featureName: String,
    onDismiss: () -> Unit,
    onFeatureReady: @Composable () -> Unit
) {
    if (featureName.isEmpty()) {
        throw IllegalArgumentException("Feature name not provided")
    }

    val manager = remember(featureName) { SplitInstallManagerFactory.create(context) }
    val isFeatureReady =
        remember(featureName) { isFeatureInstalled(manager = manager, featureName = featureName) }
    val initialState =
        remember(featureName) { if (isFeatureReady) SplitState.FeatureReady else SplitState.RequestDownload }
    val state = rememberSaveable(featureName) { mutableStateOf(initialState) }
    var statesHasChanged: Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = state.value) { statesHasChanged = true }

    if ((statesHasChanged && state.value == SplitState.FeatureReady) || state.value != SplitState.FeatureReady) {
        statesHasChanged = false
        when (state.value) {
            SplitState.RequestDownload -> RequestDownload(
                onDismiss = onDismiss,
                setState = { state.value = it })
            SplitState.Downloading -> DownloadFeature(
                featureName = featureName,
                manager = manager,
                onDismiss = onDismiss,
                setState = { state.value = it }
            )
            SplitState.FeatureReady -> {
                onDismiss()
                onFeatureReady()
            }
            SplitState.Error -> {
                LoadError(onDismiss)
            }
        }
    }
}

@Composable
internal fun LoadError(onDismiss: () -> Unit) {
    var isDialogOpen by rememberSaveable { mutableStateOf(true) }

    val arguments = DialogArguments(
        title = stringResource(id = R.string.confirmation_install_error_title),
        text = stringResource(id = R.string.confirmation_install_error_description),
        dismissText = stringResource(id = R.string.confirmation_install_ok),
    )
    DefaultDialog(arguments = arguments, isDialogOpen = isDialogOpen) {
        isDialogOpen = false
        onDismiss()
    }
}

private fun isFeatureInstalled(
    manager: SplitInstallManager,
    featureName: String,
) = manager.installedModules.contains(featureName)