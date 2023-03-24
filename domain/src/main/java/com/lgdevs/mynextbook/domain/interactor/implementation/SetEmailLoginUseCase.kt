package com.lgdevs.mynextbook.domain.interactor.implementation

fun interface SetEmailLoginUseCase : OnSetEmailLogin

typealias OnSetEmailLogin = suspend (String) -> Unit
