package com.lgdevs.mynextbook.cloudservices.auth

import kotlinx.coroutines.flow.Flow

interface CloudServicesAuth {
    suspend fun signIn(email: String, password: String): Flow<Boolean>
    suspend fun signUp(email: String, password: String): Flow<Boolean>
    suspend fun isUserRegistered(email: String): Flow<Boolean>
    suspend fun currentUser(): Flow<CurrentUser?>
    suspend fun signOut(): Flow<Unit>
    suspend fun signInWithProvider(token: String): Flow<Boolean>

}