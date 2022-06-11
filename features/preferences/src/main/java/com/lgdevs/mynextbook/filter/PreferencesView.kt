package com.lgdevs.mynextbook.filter

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.implementation.UpdatePreferencesImpl
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.filter.viewmodel.PreferencesViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PreferencesView(
    onDismiss: () -> Unit,
    viewModel: PreferencesViewModel = getViewModel()
) {
    MyNextBookTheme {

        val prefState = viewModel.preferencesState.collectAsState()
        Crossfade(targetState = prefState) { state ->
            when (val value = state.value) {
                is ViewState.Success -> {
                    Dialog(onDismissRequest = { onDismiss.invoke() }) {
                        PreferenceContent(value.result) { isEbook, keyword, isPortuguese ->
                            viewModel.setPreferences(isEbook, keyword, isPortuguese)
                        }
                    }
                }
                is ViewState.Loading -> {}
                else -> {
                    onDismiss.invoke()
                }
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
    val isPortugueseState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreferenceCheckItem(
            label = "Apenas Ebook",
            state = isEbookState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        PreferenceCheckItem(
            label = "Apenas em Português",
            state = isPortugueseState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        PreferenceInputItem(
            label = "Palavra chave",
            state = keywordState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp, bottom = 16.dp)
        )
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                onConfirm.invoke(
                    isEbookState.value,
                    keywordState.value.text,
                    isPortugueseState.value
                )
            }) {
            Text(text = "Confirmar")
        }
    }
}

@Composable
internal fun PreferenceCheckItem(
    label: String,
    state: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        Spacer(modifier = Modifier.weight(1f))
        Checkbox(checked = state.value, onCheckedChange = { state.value = it })
    }
}

@Composable
fun PreferenceInputItem(
    label: String,
    state: MutableState<TextFieldValue>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            label, modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .weight(2f)
        )
        BasicTextField(
            value = state.value,
            onValueChange = { state.value = it },
            textStyle = TextStyle(textAlign = TextAlign.End),
            modifier =
            Modifier
                .weight(3f)
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Preview(showBackground = false)
@Composable
internal fun showView() {
    PreferenceContent(
        AppPreferences(true, null, null, null),
    ) { isEbook, keyword, isPortuguese -> }
}
