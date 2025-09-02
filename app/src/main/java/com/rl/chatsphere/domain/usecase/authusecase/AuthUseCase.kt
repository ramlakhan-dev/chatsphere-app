package com.rl.chatsphere.domain.usecase.authusecase

data class AuthUseCase (
    val signUp: SignUpUseCase,
    val signIn: SignInUseCase,
    val resetPassword: ResetPasswordUseCase,
    val signOut: SignOutUseCase,
    val getCurrentUser: GetCurrentUserUseCase
)