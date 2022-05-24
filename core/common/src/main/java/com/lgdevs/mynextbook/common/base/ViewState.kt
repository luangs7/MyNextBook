package com.lgdevs.mynextbook.common.base

sealed class ViewState<out T> {
    object Loading: ViewState<Nothing>()
    object Empty: ViewState<Nothing>()
    data class Error<T>(val throwable: Throwable): ViewState<T>()
    data class Success<T>(val result: T): ViewState<T>()
}