package com.lgdevs.mynextbook.common.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

fun interface CoroutineDispatcherProvider : () -> CoroutineDispatcher
