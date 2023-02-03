package com.lgdevs.mynextbook.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.lgdevs.mynextbook.cloudservices.auth.CloudServicesAuth
import com.lgdevs.mynextbook.cloudservices.auth.CurrentUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent

class FirebaseAuthImpl : CloudServicesAuth, KoinComponent {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override suspend fun signIn(email: String, password: String): Flow<Boolean> = flow {
        val result = auth.signInWithEmailAndPassword(email, password)
        emit(result.await() != null)
    }

    override suspend fun signUp(email: String, password: String): Flow<Boolean> = flow {
        val result = auth.createUserWithEmailAndPassword(email, password)
        emit(result.await() != null)
    }

    override suspend fun isUserRegistered(email: String): Flow<Boolean> = flow {
        val result = auth.fetchSignInMethodsForEmail(email)
        val methods = result.await().signInMethods
        emit(methods.isNullOrEmpty().not())
    }

    override suspend fun currentUser(): Flow<CurrentUser?> = flow{
        auth.currentUser?.let {
            emit(
                CurrentUser(
                    it.uid,
                    it.displayName.orEmpty(),
                    it.email.orEmpty(),
                    it.photoUrl
                )
            )
        } ?: kotlin.run { emit(null) }

    }

    override suspend fun signOut(): Flow<Unit> = flow {
        emit(auth.signOut())
    }

}