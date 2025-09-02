package com.rl.chatsphere.presentation.navigation

sealed class Screen(val route: String) {

    object Welcome: Screen(route = "welcome")
    object SignUp: Screen(route = "sign-up")
    object SignIn: Screen(route = "sign-in")
    object ResetPassword: Screen(route = "reset-password")
    object Home: Screen(route = "home")
}