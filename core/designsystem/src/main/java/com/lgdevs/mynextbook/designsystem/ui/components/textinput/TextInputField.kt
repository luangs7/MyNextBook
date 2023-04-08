package com.lgdevs.mynextbook.designsystem.ui.components.textinput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lgdevs.mynextbook.designsystem.ui.theme.errorStyle

sealed class TextInputState(var state: TextFieldValue) {
    data class Success(val field: TextFieldValue) : TextInputState(field)
    data class Error(val field: TextFieldValue, val message: String) : TextInputState(field)
    data class Default(val field: TextFieldValue) : TextInputState(field)

}

fun MutableState<TextInputState>.text(): String = this.value.state.text
fun MutableState<TextInputState>.text(text: String) {
    this.value.state = TextFieldValue(text)
}

@ExperimentalMaterialApi
@Composable
fun TextInputField(
    label: String,
    fieldState: MutableState<TextInputState>,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,
    onValueChanged: (TextFieldValue) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val state = remember { mutableStateOf(fieldState.value.state) }
    val isError = fieldState.value is TextInputState.Error
    val color = if (isError) Color.Red else Color.White

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = state.value,
            onValueChange = {
                state.value = it
                onValueChanged(it)
            },
            textStyle = TextStyle(color = color, textAlign = TextAlign.Start),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Done
            ),
            modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body1.copy(color = color),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White.copy(0.8f)
            ),
            visualTransformation = visualTransformation
        )
        if (isError) {
            Text(
                text = (fieldState.value as TextInputState.Error).message,
                style = errorStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }

}