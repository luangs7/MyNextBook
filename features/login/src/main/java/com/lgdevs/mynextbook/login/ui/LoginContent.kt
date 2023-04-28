package com.lgdevs.mynextbook.login.ui

import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.Identity
import com.lgdevs.mynextbook.common.R
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.designsystem.ui.components.lottie.LottieView
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.LoadingView
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.model.ViewStateParam
import com.lgdevs.mynextbook.designsystem.ui.components.textinput.TextInputState
import com.lgdevs.mynextbook.designsystem.ui.components.textinput.text
import com.lgdevs.mynextbook.login.viewmodel.LoginViewModel
import com.lgdevs.mynextbook.navigation.NavigationItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun LoginContent(
    navController: NavController,
    viewModel: LoginViewModel,
    getEmailState: String,
) {
    val emailState =
        remember {
            mutableStateOf<TextInputState>(
                TextInputState.Default(
                    TextFieldValue(
                        getEmailState,
                    ),
                ),
            )
        }
    val passwordState =
        remember { mutableStateOf<TextInputState>(TextInputState.Default(TextFieldValue())) }
    var loadingState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val onLoginErrorListener = {
        emailState.value =
            TextInputState.Error(TextFieldValue(emailState.text()), "")
        passwordState.value = TextInputState.Error(
            TextFieldValue(passwordState.text()),
            "E-mail ou senha incorretos",
        )
    }

    val onLoginLoadingListener: (Boolean) -> Unit = { loadingState = it }
    val onLoginSuccessListener = { navController.navigate(NavigationItem.Welcome.route) }

    val context = LocalContext.current
    val oneTapClient = Identity.getSignInClient(context)
    val signInRequest = LoginFactory.beginSignInRequest()
    val launcher =
        LoginFactory.doLauncher(oneTapClient = oneTapClient, onCredentialsListener = { credential ->
            val idToken = credential.googleIdToken
            if (idToken != null) {
                viewModel.doLoginWithToken(String(), idToken).handleLogin(
                    onLoginLoadingListener,
                    onLoginSuccessListener,
                    onLoginErrorListener,
                )
                    .launchIn(scope)
            }
        })

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        val guideline = createGuidelineFromTop(0.3f)
        val (lottieGif, content) = createRefs()
        val (footer, loading) = createRefs()

        if (loadingState) {
            LoginLoading(
                modifier = Modifier
                    .constrainAs(loading) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    },
            )
        }

        LottieView(
            url = "https://assets2.lottiefiles.com/packages/lf20_ad3uxjiv.json",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(lottieGif) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(guideline)
                    height = Dimension.fillToConstraints
                },
        )
        LoginDataContent(
            emailState,
            passwordState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(content) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(guideline)
                },
        )
        LoginFooter(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .constrainAs(footer) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            onGoogleListener = {
                scope.launch {
                    val result = oneTapClient.beginSignIn(signInRequest).await()
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent).build()
                    launcher.launch(intentSenderRequest)
                }
            },
            onSignIn = {
                doSign(
                    viewModel = viewModel,
                    scope = scope,
                    OnLoginErrorListener = onLoginErrorListener,
                    OnLoginLoadingListener = onLoginLoadingListener,
                    OnLoginSuccessListener = onLoginSuccessListener,
                    password = passwordState.text(),
                    email = emailState.text(),
                )
            },
            showGoogleButton = viewModel.showGoogleButton(),
        )
    }
}

@Composable
private fun LoginLoading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        LoadingView(stateParam = ViewStateParam(animation = R.raw.lottie_loading))
    }
}

private fun doSign(
    viewModel: LoginViewModel,
    scope: CoroutineScope,
    OnLoginLoadingListener: OnLoginLoading,
    OnLoginSuccessListener: OnLoginSuccess,
    OnLoginErrorListener: OnLoginError,
    email: String,
    password: String,

) {
    scope.launch {
        viewModel.doLogin(email, password).handleLogin(
            OnLoginLoadingListener,
            OnLoginSuccessListener,
            OnLoginErrorListener,
        )
            .launchIn(this)
    }
}

internal fun Flow<ViewState<Boolean>>.handleLogin(
    onLoginLoadingListener: OnLoginLoading,
    onLoginSuccessListener: OnLoginSuccess,
    onLoginErrorListener: OnLoginError,
): Flow<ViewState<Boolean>> {
    return this.onEach { state ->
        onLoginLoadingListener.invoke(state is ViewState.Loading)
        when (state) {
            ViewState.Empty,
            is ViewState.Error,
            -> onLoginErrorListener.invoke()

            is ViewState.Success -> onLoginSuccessListener.invoke()
            else -> {}
        }
    }
        .distinctUntilChanged()
}
