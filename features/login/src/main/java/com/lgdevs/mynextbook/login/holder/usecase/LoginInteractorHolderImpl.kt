package com.lgdevs.mynextbook.login.holder.usecase

import com.lgdevs.mynextbook.domain.interactor.implementation.DoLoginUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.DoLoginWithTokenUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetEmailLoginUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.SetEmailLoginUseCase

class LoginInteractorHolderImpl(
    private val loginUseCase: DoLoginUseCase,
    private val userUseCase: GetUserUseCase,
    private val setEmailLoginUseCase: SetEmailLoginUseCase,
    private val getEmailLoginUseCase: GetEmailLoginUseCase,
    private val doLoginWithTokenUseCase: DoLoginWithTokenUseCase,
) : LoginInteractorHolder {
    override fun getLoginUseCase(): DoLoginUseCase = loginUseCase

    override fun getUserUseCase(): GetUserUseCase = userUseCase

    override fun getSaveEmailUseCase(): SetEmailLoginUseCase = setEmailLoginUseCase

    override fun getEmailUseCase(): GetEmailLoginUseCase = getEmailLoginUseCase

    override fun getLoginWithTokenUseCase(): DoLoginWithTokenUseCase = doLoginWithTokenUseCase
}
