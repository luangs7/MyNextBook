package com.lgdevs.mynextbook.common.dispatcher

import kotlinx.coroutines.Dispatchers

object CoroutineDispatcherProviderImpl {
    fun io() = Dispatchers.IO
    fun default() = Dispatchers.Default
    fun main() = Dispatchers.Main
    fun unconfined() = Dispatchers.Unconfined
}
