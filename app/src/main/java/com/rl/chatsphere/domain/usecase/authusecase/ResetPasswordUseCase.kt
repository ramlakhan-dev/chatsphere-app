package com.rl.chatsphere.domain.usecase.authusecase

import com.rl.chatsphere.domain.model.ResetPasswordResult
import com.rl.chatsphere.domain.repository.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): ResetPasswordResult = authRepository.sendResetPasswordLink(email)
}