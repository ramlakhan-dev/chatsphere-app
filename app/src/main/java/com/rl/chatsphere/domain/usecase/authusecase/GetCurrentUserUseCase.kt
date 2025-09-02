package com.rl.chatsphere.domain.usecase.authusecase

import com.google.firebase.auth.FirebaseUser
import com.rl.chatsphere.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): FirebaseUser? = authRepository.getCurrentUser()
}