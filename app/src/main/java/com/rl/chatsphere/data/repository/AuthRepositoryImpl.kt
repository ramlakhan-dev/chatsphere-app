package com.rl.chatsphere.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rl.chatsphere.domain.model.AuthResult
import com.rl.chatsphere.domain.model.ResetPasswordResult
import com.rl.chatsphere.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override suspend fun signUp(
        email: String,
        password: String
    ): AuthResult {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Authenticated
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Sign up failed")
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): AuthResult {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Authenticated
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Sign in failed")
        }
    }

    override suspend fun sendResetPasswordLink(email: String): ResetPasswordResult {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            ResetPasswordResult.ResetPasswordLineSent
        } catch (e: Exception) {
            ResetPasswordResult.Error(e.message ?: "Failed to send reset password link")
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}