package com.lgdevs.mynextbook.domain.interactor.implementation

import kotlinx.coroutines.flow.Flow

fun interface GetEmailLoginUseCase: suspend () -> Flow<String>
