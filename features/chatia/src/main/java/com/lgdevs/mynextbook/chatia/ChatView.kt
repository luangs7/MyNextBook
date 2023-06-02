package com.lgdevs.mynextbook.chatia

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lgdevs.mynextbook.chatia.model.Message

@Composable
fun ChatView() {
    val chatMessages = remember {
        mutableStateListOf<Message>(
            Message("Hello", true),
            Message("Hello!", false),
            Message("How can I help you?", false),
            Message("Nah Im good", true),
        )
    }
    val messageToSend = remember { mutableStateOf("") }

    Column() {
        Box(Modifier.weight(1f)) {
            ChatMessageList(chatMessages)
        }
        ChatInput(message = messageToSend.value, onMessageChange = {
            messageToSend.value = it
        }, onSendClicked = {
            chatMessages.add(Message(messageToSend.value, true))
            messageToSend.value = ""
        })
    }
}

@Composable
fun ChatMessageList(chatMessages: List<Message>) {
    LazyColumn(modifier = Modifier.fillMaxSize(), reverseLayout = true) {
        items(chatMessages.asReversed()) { message ->
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
            Text(modifier = Modifier.padding(16.dp), text = message.text)
        }
        Text("22:32", color = Color.White.copy(0.5f), fontSize = 10.sp, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun ChatInput(
    message: String,
    onMessageChange: (String) -> Unit,
    onSendClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = { onSendClicked.invoke() }),
            value = message,
            onValueChange = onMessageChange,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            label = { Text("Type your message...") },
        )
        IconButton(
            onClick = onSendClicked,
            modifier = Modifier.align(Alignment.CenterVertically),
        ) {
            Icon(Icons.Filled.Send, contentDescription = "Send message", tint = Color.Black)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ChatPreview() {
    ChatView()
}
