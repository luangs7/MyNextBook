package com.lgdevs.mynextbook.chatia

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lgdevs.mynextbook.chatia.domain.model.Message
import com.lgdevs.mynextbook.chatia.presentation.ChatViewModel
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.common.compose.onViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
@Composable
fun ChatView(
    viewModel: ChatViewModel = getViewModel(),
) {
    val state = viewModel.chatMessagesFlow.collectAsState(initial = ViewState.Empty)
    val listState = rememberLazyListState()

    Crossfade(targetState = state) {
        Column {
            Box(Modifier.weight(1f)) {
                onViewState(
                    state = it.value,
                ) { data ->
                    data?.let {
                        LaunchedEffect(it.size) {
                            listState.animateScrollToItem(0)
                        }
                        ChatMessageList(it, listState)
                    }
                }
            }
            ChatInput(viewModel = viewModel) {
            }
        }
    }
}

@Composable
fun ChatMessageList(chatMessages: List<Message>, listState: LazyListState) {
    LazyColumn(state = listState, modifier = Modifier.fillMaxSize(), reverseLayout = true) {
        items(items = chatMessages, key = { it.id }) { message ->
            MessageCard(message)
        }
    }
}

@Composable
fun MessageCard(
    message: Message,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = if (message.sender) Alignment.End else Alignment.Start,
    ) {
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = if (message.sender) RoundedCornerShape(16.dp).copy(bottomEnd = CornerSize(0.dp)) else RoundedCornerShape(16.dp).copy(bottomStart = CornerSize(0.dp)),
            backgroundColor = if (message.sender) Color.Blue.copy(0.3f) else Color(0xFF2C213F),
        ) {
            Text(modifier = Modifier.padding(16.dp), text = message.message)
        }
        Text(message.datetime, color = Color.White.copy(0.5f), fontSize = 10.sp, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun ChatInput(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel,
    onSend: () -> Unit,
) {
    val messageToSend = remember { mutableStateOf(String()) }
    val inputState: MutableState<ViewState<String?>> = remember { mutableStateOf(ViewState.Empty) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = inputState.value) {
        if (inputState.value !is ViewState.Loading) {
            messageToSend.value = String()
            onSend()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send,
                ),
                keyboardActions = KeyboardActions(onSend = {
                    sendQuestion(
                        viewModel,
                        messageToSend.value,
                        scope,
                    ) {
                        inputState.value = it
                    }
                }),
                value = messageToSend.value,
                onValueChange = {
                    messageToSend.value = it
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                label = { Text("Type your message...") },
            )
            IconButton(
                onClick = {
                    sendQuestion(
                        viewModel,
                        messageToSend.value,
                        scope,
                    ) {
                        inputState.value = it
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically),
            ) {
                Icon(Icons.Filled.Send, contentDescription = "Send message", tint = Color.Black)
            }
        }
    }
}

private fun sendQuestion(
    viewModel: ChatViewModel,
    message: String,
    scope: CoroutineScope,
    onEach: (ViewState<String?>) -> Unit,
) {
    scope.launch {
        viewModel.sendQuestion(message)
            .onEach { onEach.invoke(it) }
            .distinctUntilChanged()
            .launchIn(this)
    }
}

@Composable
@Preview(showBackground = true)
fun ChatPreview() {
    ChatView()
}
