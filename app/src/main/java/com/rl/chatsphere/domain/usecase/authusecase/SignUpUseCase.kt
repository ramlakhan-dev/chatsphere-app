package com.rl.chatsphere.domain.usecase.authusecase

import com.rl.chatsphere.domain.model.AuthResult
import com.rl.chatsphere.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AuthResult = authRepository.signUp(email, password)
}