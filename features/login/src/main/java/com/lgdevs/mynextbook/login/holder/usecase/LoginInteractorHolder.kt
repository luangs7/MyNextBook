package com.lgdevs.mynextbook.login.holder.usecase

import com.lgdevs.mynextbook.domain.interactor.implementation.DoLoginUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.DoLoginWithTokenUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetEmailLoginUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.SetEmailLoginUseCase

interface LoginInteractorHolder {
    fun getLoginUseCase(): DoLoginUseCase
    fun getUserUseCase(): GetUserUseCase
    fun getSaveEmailUseCase(): SetEmailLoginUseCase
    fun getEmailUseCase(): GetEmailLoginUseCase
    fun getLoginWithTokenUseCase(): DoLoginWithTokenUseCase
}
