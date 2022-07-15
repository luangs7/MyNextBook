@file:OptIn(ExperimentalComposeUiApi::class)

package com.lgdevs.mynextbook.filter.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.designsystem.ui.theme.blackWithTransparency
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.filter.R
import com.lgdevs.mynextbook.filter.ui.components.PreferenceCheckItem
import com.lgdevs.mynextbook.filter.ui.components.PreferenceInputItem
import com.lgdevs.mynextbook.filter.viewmodel.PreferencesViewModel
import org.koin.androidx.compose.getViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun PreferencesView(
    onDismiss: () -> Unit,
    viewModel: PreferencesViewModel = getViewModel()
) {
    MyNextBookTheme {
        val prefState = viewModel.getPreferences().collectAsState(ViewState.Loading)
        Crossfade(targetState = prefState) { state ->
            when (val value = state.value) {
                is ViewState.Success -> PreferenceDialog(value.result, onDismiss)
                is ViewState.Loading -> {}
                else ->  onDismiss()
            }
        }
    }
}

@Composable
internal fun PreferenceDialog(
    model: AppPreferences,
    onDismiss: () -> Unit,
    viewModel: PreferencesViewModel = getViewModel()
){
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {

        val saveState = viewModel.addPreferences.collectAsState(initial = null)

        PreferenceContent(model) { isEbook, keyword, isPortuguese ->
            viewModel.setPreferences(isEbook, keyword, isPortuguese)
        }

        LaunchedEffect(key1 = saveState.value){
            when(saveState.value){
                is ViewState.Success -> onDismiss()
                else -> {}
            }
        }
    }
}

@Composable
internal fun PreferenceContent(
    model: AppPreferences,
    onConfirm: (Boolean, String?, Boolean) -> Unit
) {
    val isEbookState = remember { mutableStateOf(model.isEbook) }
    val keywordState = remember { mutableStateOf(TextFieldValue(model.keyword.orEmpty())) }
    val isPortugueseState = remember { mutableStateOf(model.isPortuguese) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .background(blackWithTransparency),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreferenceCheckItem(
            label = stringResource(R.string.ebook_only),
            state = isEbookState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        PreferenceCheckItem(
            label = stringResource(R.string.language_title),
            state = isPortugueseState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        PreferenceInputItem(
            label = stringResource(R.string.keyword_title),
            hint = stringResource(R.string.keyword_description),
            state = keywordState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp, bottom = 16.dp)
        )
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                onConfirm(
                    isEbookState.value,
                    keywordState.value.text,
                    isPortugueseState.value
                )
            }) {
            Text(text = stringResource(R.string.confirm), style = MaterialTheme.typography.body1)
        }
    }
}


@Preview(showBackground = false)
@Composable
internal fun showView() {
    PreferenceContent(
        AppPreferences(true, null, false, null),
    ) { _, _, _ -> }
}
