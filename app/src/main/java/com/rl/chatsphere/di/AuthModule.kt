package com.rl.chatsphere.di

import com.google.firebase.auth.FirebaseAuth
import com.rl.chatsphere.data.repository.AuthRepositoryImpl
import com.rl.chatsphere.domain.repository.AuthRepository
import com.rl.chatsphere.domain.usecase.authusecase.AuthUseCase
import com.rl.chatsphere.domain.usecase.authusecase.GetCurrentUserUseCase
import com.rl.chatsphere.domain.usecase.authusecase.ResetPasswordUseCase
import com.rl.chatsphere.domain.usecase.authusecase.SignInUseCase
import com.rl.chatsphere.domain.usecase.authusecase.SignOutUseCase
import com.rl.chatsphere.domain.usecase.authusecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository): AuthUseCase {
        return AuthUseCase(
            signUp = SignUpUseCase(authRepository),
            signIn = SignInUseCase(authRepository),
            resetPassword = ResetPasswordUseCase(authRepository),
            signOut = SignOutUseCase(authRepository),
            getCurrentUser = GetCurrentUserUseCase(authRepository)
        )
    }
}