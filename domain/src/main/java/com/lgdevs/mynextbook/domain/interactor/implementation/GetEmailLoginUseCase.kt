package com.lgdevs.mynextbook.domain.interactor.implementation

import kotlinx.coroutines.flow.Flow

fun interface GetEmailLoginUseCase: OnGetEmailLogin

typealias OnGetEmailLogin = suspend () -> Flow<String>
