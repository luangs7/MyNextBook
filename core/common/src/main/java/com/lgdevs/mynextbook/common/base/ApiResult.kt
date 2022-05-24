package com.lgdevs.mynextbook.common.base

sealed class ApiResult<out T> {
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Error<T>(val error: Throwable) : ApiResult<T>()
    object Loading : ApiResult<Nothing>()
    object Empty : ApiResult<Nothing>()
}

