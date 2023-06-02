package com.lgdevs.mynextbook.chatia.domain.interaction

import com.lgdevs.mynextbook.chatia.domain.model.Message
import com.lgdevs.mynextbook.common.base.ApiResult
import kotlinx.coroutines.flow.Flow

fun interface SaveMessageUseCase : suspend (Message) -> Flow<ApiResult<Unit>>
