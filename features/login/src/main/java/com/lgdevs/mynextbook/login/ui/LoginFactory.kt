package com.lgdevs.mynextbook.login.ui

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential

typealias OnCredentialsListener = (SignInCredential) -> Unit

object LoginFactory {

    fun beginSignInRequest(): BeginSignInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                // Your server's client ID, not your Android client ID.
                .setServerClientId("417858212880-htig812klg5nlhrftnkgbj6h7q24qmg4.apps.googleusercontent.com")
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        // Automatically sign in when exactly one credential is retrieved.
        .setAutoSelectEnabled(true)
        .build()

    @Composable
    fun doLauncher(
        oneTapClient: SignInClient,
        onCredentialsListener: OnCredentialsListener
    ): ActivityResultLauncher<IntentSenderRequest> = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            return@rememberLauncherForActivityResult
        }
        onCredentialsListener.invoke(oneTapClient.getSignInCredentialFromIntent(result.data))
    }
}