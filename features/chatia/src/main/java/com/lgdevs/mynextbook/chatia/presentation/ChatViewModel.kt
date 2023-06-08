package com.lgdevs.mynextbook.chatia.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgdevs.mynextbook.chatia.domain.interaction.GetMessagesUseCase
import com.lgdevs.mynextbook.chatia.domain.interaction.SaveMessageUseCase
import com.lgdevs.mynextbook.chatia.domain.interaction.SendQuestionUseCase
import com.lgdevs.mynextbook.chatia.domain.model.Message
import com.lgdevs.mynextbook.chatia.domain.model.createMessage
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.domain.model.User
import com.lgdevs.mynextbook.extensions.collectIfSuccess
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

class ChatViewModel(
    private val setMessageUseCase: SaveMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendQuestionUseCase: SendQuestionUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private var currentUser: User? = null

    private val chatSharedFlow: MutableSharedFlow<Unit> =
        MutableSharedFlow(replay = 1)

    val chatMessagesFlow = chatSharedFlow.flatMapMerge {
        getMessageList().catch { emit(ViewState.Error(it)) }
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    init {
        chatSharedFlow.tryEmit(Unit)
    }
    private fun getMessageList() = flow {
        currentUser?.let { currentUser ->
            getMessages(currentUser).collect(this::emit)
        } ?: run {
            getUser().collect(this::emit)
        }
    }

    private fun getMessages(user: User) = flow {
        getMessagesUseCase.invoke(user.uuid).collect {
            emit(afterGetMessages(it))
        }
    }

    private suspend fun getUser() = flow {
        getUserUseCase().collectIfSuccess { user ->
            currentUser = user
            getMessages(user).collect(this::emit)
        }
    }
    private fun afterGetMessages(result: ApiResult<List<Message>>): ViewState<List<Message>?> {
        return when (result) {
            ApiResult.Empty -> ViewState.Empty
            is ApiResult.Error -> ViewState.Error(result.error)
            ApiResult.Loading -> ViewState.Loading
            is ApiResult.Success -> ViewState.Success(result.data)
        }
    }

    fun sendQuestion(message: String) = flow {
        currentUser?.let { user ->
            val messageSent = createMessage(user.uuid, message, true)
            setMessageUseCase(user.uuid, messageSent).collect()
            sendQuestionUseCase(message).collect {
                emit(afterSendQuestion(user.uuid, it))
            }
            addMessage(messageSent)
        }
    }

    private suspend fun afterSendQuestion(userId: String, result: ApiResult<String>): ViewState<String?> {
        return when (result) {
            ApiResult.Empty -> ViewState.Empty
            is ApiResult.Error -> ViewState.Error(result.error)
            ApiResult.Loading -> ViewState.Loading
            is ApiResult.Success -> {
                val iaMessage = createMessage(userId, result.data.orEmpty(), false)
                addMessage(iaMessage)
                setMessageUseCase(userId, iaMessage).collect()
                ViewState.Success(result.data)
            }
        }
    }

    private fun addMessage(message: Message) {
        chatSharedFlow.tryEmit(Unit)
    }
}
