package com.rl.chatsphere.domain.usecase.authusecase

import com.rl.chatsphere.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.signOut()
}