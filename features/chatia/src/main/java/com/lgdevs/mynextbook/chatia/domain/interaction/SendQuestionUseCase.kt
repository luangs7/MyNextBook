package com.lgdevs.mynextbook.chatia.domain.interaction

import com.lgdevs.mynextbook.common.base.ApiResult
import kotlinx.coroutines.flow.Flow

fun interface SendQuestionUseCase : suspend (String) -> Flow<ApiResult<String>>
