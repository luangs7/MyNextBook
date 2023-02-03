package com.lgdevs.mynextbook.domain.interactor.implementation

import kotlinx.coroutines.flow.Flow

fun interface SetEmailLoginUseCase: suspend (String) -> Flow<Unit>
