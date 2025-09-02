package com.rl.chatsphere.domain.model

sealed class ResetPasswordResult {
    object ResetPasswordLineSent: ResetPasswordResult()
    data class Error(val message: String?): ResetPasswordResult()
}