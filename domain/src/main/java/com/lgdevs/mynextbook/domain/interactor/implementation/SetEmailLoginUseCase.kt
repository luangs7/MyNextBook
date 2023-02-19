package com.lgdevs.mynextbook.domain.interactor.implementation

import kotlinx.coroutines.flow.Flow

fun interface SetEmailLoginUseCase: OnSetEmailLogin

typealias OnSetEmailLogin = suspend (String) -> Flow<Unit>