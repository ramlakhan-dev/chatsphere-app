package com.rl.chatsphere.presentation.screen.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rl.chatsphere.domain.model.ResetPasswordResult
import com.rl.chatsphere.domain.usecase.authusecase.AuthUseCase
import com.rl.chatsphere.presentation.state.ResetPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
): ViewModel() {

    private val _resetPasswordState = MutableStateFlow<ResetPasswordState>(ResetPasswordState.Idle)
    val resetPasswordState: StateFlow<ResetPasswordState> = _resetPasswordState.asStateFlow()

    fun sendResetPasswordLink(email: String) {
        if (email.isEmpty()) {
            _resetPasswordState.value = ResetPasswordState.Error("Email can't be empty")
            return
        }

        viewModelScope.launch {
            _resetPasswordState.value = ResetPasswordState.Loading

            val result = authUseCase.resetPassword(email)
            _resetPasswordState.value = when(result) {
                is ResetPasswordResult.ResetPasswordLineSent -> ResetPasswordState.ResetPasswordLinkSent
                is ResetPasswordResult.Error -> ResetPasswordState.Error(result.message)
            }
        }
    }
}