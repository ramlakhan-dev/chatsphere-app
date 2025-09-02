package com.rl.chatsphere.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.rl.chatsphere.domain.model.AuthResult
import com.rl.chatsphere.domain.model.ResetPasswordResult

interface AuthRepository {

    suspend fun signUp(email: String, password: String): AuthResult
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun sendResetPasswordLink(email: String): ResetPasswordResult
    fun signOut()
    fun getCurrentUser(): FirebaseUser?
}