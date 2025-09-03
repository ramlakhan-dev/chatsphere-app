package com.rl.chatsphere.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.rl.chatsphere.domain.usecase.authusecase.AuthUseCase
import com.rl.chatsphere.presentation.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
): ViewModel() {

    private val _userState = MutableStateFlow<AuthState>(AuthState.Idle)
    val userState: StateFlow<AuthState> = _userState.asStateFlow()

    private val _signOutState = MutableStateFlow<AuthState>(AuthState.Idle)
    val signOutState: StateFlow<AuthState> = _signOutState.asStateFlow()

    init {
        val user = authUseCase.getCurrentUser()
        if (user != null) {
            _userState.value = AuthState.Authenticated
        } else {
            _userState.value = AuthState.Unauthenticated
        }
    }

    fun signOut() {
        authUseCase.signOut()
        _signOutState.value = AuthState.Unauthenticated
    }
}