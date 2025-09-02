package com.rl.chatsphere.presentation.state

sealed class ResetPasswordState {
    object Idle: ResetPasswordState()
    object Loading: ResetPasswordState()
    object ResetPasswordLinkSent: ResetPasswordState()
    data class Error(val message: String?): ResetPasswordState()
}