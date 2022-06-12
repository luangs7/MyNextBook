package com.lgdevs.mynextbook.filter.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lgdevs.mynextbook.designsystem.ui.theme.placeholder

@Composable
fun PreferenceInputItem(
    label: String,
    hint: String,
    state: MutableState<TextFieldValue>,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label, modifier = Modifier
                .wrapContentWidth()
        )
        BasicTextField(
            value = state.value,
            onValueChange = { state.value = it },
            textStyle = TextStyle(color = Color.White, textAlign = TextAlign.Center),
            cursorBrush = SolidColor(Color.White),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    if (state.value.text.isEmpty()) {
                        Text(
                            text = hint,
                            style = placeholder
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}
