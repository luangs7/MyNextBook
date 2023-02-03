@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package com.lgdevs.mynextbook.login

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.designsystem.ui.components.dialog.DefaultDialog
import com.lgdevs.mynextbook.designsystem.ui.components.dialog.DialogArguments
import com.lgdevs.mynextbook.designsystem.ui.components.googlebutton.GoogleButton
import com.lgdevs.mynextbook.designsystem.ui.components.lottie.LottieView
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.LoadingView
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.model.ViewStateParam
import com.lgdevs.mynextbook.designsystem.ui.components.textinput.TextInputField
import com.lgdevs.mynextbook.designsystem.ui.components.textinput.TextInputState
import com.lgdevs.mynextbook.designsystem.ui.components.textinput.text
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import com.lgdevs.mynextbook.designsystem.ui.theme.linked
import com.lgdevs.mynextbook.navigation.NavigationItem
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginView(
    navController: NavController,
    viewModel: LoginViewModel = getViewModel()
) {
    val userState = viewModel.userFlow.collectAsState(initial = ViewState.Loading)
    var hasRedirected by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) { viewModel.getUser() }
    Crossfade(targetState = userState) { state ->
        when (state.value) {
            ViewState.Empty,
            is ViewState.Error -> {
                Login(navController, viewModel)
            }
            is ViewState.Success -> if (hasRedirected.not()) {
                navController.navigate(NavigationItem.Welcome.route)
                hasRedirected = true
            }
            else -> {}
        }
    }
}

@Composable
private fun Login(
    navController: NavController,
    viewModel: LoginViewModel
) {
    viewModel.onGetEmail().collectAsState(initial = null).value?.let {
        MyNextBookTheme {
            LoginContent(navController = navController, viewModel, it)
        }
    }
}

@Composable
private fun LoginContent(
    navController: NavController,
    viewModel: LoginViewModel,
    getEmailState: String
) {

    val emailState =
        remember {
            mutableStateOf<TextInputState>(
                TextInputState.Default(
                    TextFieldValue(
                        getEmailState
                    )
                )
            )
        }
    val passwordState =
        remember { mutableStateOf<TextInputState>(TextInputState.Default(TextFieldValue())) }
    var loadingState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val guideline = createGuidelineFromTop(0.3f)
        val (lottieGif, content, footer, loading) = createRefs()

        if (loadingState) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(loading) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }) {
                LoadingView(stateParam = ViewStateParam(animation = com.lgdevs.mynextbook.common.R.raw.lottie_error))
            }
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
                }
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
                }
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
            onGoogleListener = {},
            onSignIn = {
                scope.launch {
                    viewModel.doLogin(emailState.text(), passwordState.text())
                        .onEach { state ->
                            loadingState = state is ViewState.Loading
                            when (state) {
                                ViewState.Empty,
                                is ViewState.Error -> {
                                    emailState.value =
                                        TextInputState.Error(TextFieldValue(emailState.text()), "")
                                    passwordState.value = TextInputState.Error(
                                        TextFieldValue(passwordState.text()),
                                        "E-mail ou senha incorretos"
                                    )
                                }
                                is ViewState.Success -> navController.navigate(NavigationItem.Welcome.route)
                                else -> {}
                            }
                        }
                        .distinctUntilChanged()
                        .launchIn(this)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LoginDataContent(
    emailState: MutableState<TextInputState>,
    passwordState: MutableState<TextInputState>,
    modifier: Modifier = Modifier
) {

    var requestDialog by rememberSaveable { mutableStateOf(false) }

    if (requestDialog) {
        SignupModal {
            requestDialog = false
        }
    }

    Column(
        modifier = modifier
    ) {
        TextInputField(
            label = stringResource(id = R.string.email_field),
            fieldState = emailState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            emailState.value = TextInputState.Default(TextFieldValue(it.text))
        }
        TextInputField(
            label = stringResource(id = R.string.password_field),
            fieldState = passwordState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            visualTransformation = PasswordVisualTransformation()
        ) {
            passwordState.value = TextInputState.Default(TextFieldValue(it.text))
        }
        Text(
            text = stringResource(id = R.string.signup), style = linked, textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, end = 16.dp)
                .clickable {
                    requestDialog = true
                }
        )
    }
}

@Composable
private fun LoginFooter(
    modifier: Modifier = Modifier,
    onGoogleListener: () -> Unit,
    onSignIn: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoogleButton(
            text = stringResource(id = R.string.signin_google),
            textColor = Color.Black,
            backgroundColor = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            onGoogleListener.invoke()
        }
        Button(
            onClick = { onSignIn.invoke() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.signin),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SignupModal(
    onDismiss: () -> Unit
) {
    var isDialogOpen by rememberSaveable { mutableStateOf(true) }

    val arguments = DialogArguments(
        title = stringResource(id = R.string.signup_title),
        text = stringResource(id = R.string.signup_description),
        dismissText = stringResource(id = R.string.confirmation_button)
    )

    DefaultDialog(
        arguments = arguments,
        isDialogOpen = isDialogOpen,
        onDismissRequest = {
            isDialogOpen = false
            onDismiss()
        }
    )
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun LoginPreview() {
    LoginView(navController = rememberNavController())
}