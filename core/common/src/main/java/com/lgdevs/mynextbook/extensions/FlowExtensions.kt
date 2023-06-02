package com.lgdevs.mynextbook.extensions

import com.lgdevs.mynextbook.common.base.ApiResult
import kotlinx.coroutines.flow.Flow

suspend fun <T> Flow<ApiResult<T?>>.collectIfSuccess(doCollect: suspend (T) -> Unit) {
    collect {
        if (it is ApiResult.Success && it.data != null) {
            doCollect.invoke(it.data)
        }
    }
}
