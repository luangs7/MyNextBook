package com.lgdevs.mynextbook.login.analytics

import android.os.Bundle
import com.lgdevs.mynextbook.common.analytics.BaseAnalytics
import com.lgdevs.mynextbook.domain.model.User

interface LoginAnalytics : BaseAnalytics {
    companion object {
        const val LOGIN_SCREEN_VIEW = "ScrViewLoginAnalytics"
        const val BUTTON_LOGIN_ENTER = "BtnEnterLogin"
        const val BUTTON_LOGIN_ENTER_GOOGLE = "BtnEnterLoginGoogle"
        const val LOGIN_USERNAME = "LoginUsernameField"
        const val LOGIN_EMAIL = "LoginEmailField"
        const val LOGIN_WITH_GOOGLE_ERROR = "LoginGoogleError"
        const val LOGIN_WITH_ERROR = "LoginError"
        const val LOGIN_USER = "LoginUser"
    }

    fun createParams(param: User): Bundle
}
