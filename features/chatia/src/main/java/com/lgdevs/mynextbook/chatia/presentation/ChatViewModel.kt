package com.lgdevs.mynextbook.chatia.presentation

import androidx.lifecycle.ViewModel
import com.lgdevs.mynextbook.chatia.domain.interaction.GetMessagesUseCase
import com.lgdevs.mynextbook.chatia.domain.interaction.SaveMessageUseCase
import com.lgdevs.mynextbook.chatia.domain.interaction.SendQuestionUseCase
import com.lgdevs.mynextbook.chatia.domain.model.Message
import com.lgdevs.mynextbook.chatia.domain.model.createMessage
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.extensions.collectIfSuccess
import kotlinx.coroutines.flow.flow

class ChatViewModel(
    private val setMessageUseCase: SaveMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendQuestionUseCase: SendQuestionUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    fun getMessageList() = flow {
        getUserUseCase().collectIfSuccess {
            getMessagesUseCase.invoke(it.uuid).collect { result ->
                emit(afterGetMessages(result))
            }
        }
    }

    private fun afterGetMessages(result: ApiResult<List<Message>>): ViewState<List<Message>> {
        return when (result) {
            ApiResult.Empty -> ViewState.Empty
            is ApiResult.Error -> ViewState.Error(result.error)
            ApiResult.Loading -> ViewState.Loading
            is ApiResult.Success -> {
                if (result.data.isNullOrEmpty()) ViewState.Empty else ViewState.Success(result.data!!)
            }
        }
    }

    fun sendQuestion(message: String) = flow {
        getUserUseCase().collectIfSuccess {
            setMessageUseCase(createMessage(it.uuid, message, true))
            sendQuestionUseCase(message).collect { result ->
                emit(afterSendQuestion(it.uuid, result))
            }
        }
    }

    private suspend fun afterSendQuestion(userId: String, result: ApiResult<String>): ViewState<String?> {
        return when (result) {
            ApiResult.Empty -> ViewState.Empty
            is ApiResult.Error -> ViewState.Error(result.error)
            ApiResult.Loading -> ViewState.Loading
            is ApiResult.Success -> {
                setMessageUseCase(createMessage(userId, result.data.orEmpty(), false))
                ViewState.Success(result.data)
            }
        }
    }
}
