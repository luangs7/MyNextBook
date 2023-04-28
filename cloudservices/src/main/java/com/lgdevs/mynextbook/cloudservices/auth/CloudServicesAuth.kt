package com.lgdevs.mynextbook.cloudservices.auth

import kotlinx.coroutines.flow.Flow

interface CloudServicesAuth {
    fun signIn(email: String, password: String): Flow<Boolean>
    fun signUp(email: String, password: String): Flow<Boolean>
    fun isUserRegistered(email: String): Flow<Boolean>
    fun currentUser(): Flow<CurrentUser?>
    fun signOut(): Flow<Unit>
    fun signInWithProvider(token: String): Flow<Boolean>
}
