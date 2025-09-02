package com.rl.chatsphere.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rl.chatsphere.presentation.screen.home.HomeScreen
import com.rl.chatsphere.presentation.screen.resetpassword.ResetPasswordScreen
import com.rl.chatsphere.presentation.screen.signin.SignInScreen
import com.rl.chatsphere.presentation.screen.signup.SignUpScreen
import com.rl.chatsphere.presentation.screen.welcome.WelcomeScreen


@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isAuthenticated: Boolean
) {

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) Screen.Home.route else Screen.Welcome.route
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                modifier = modifier,
                onSignUpClick = { navController.navigate(route = Screen.SignUp.route) },
                onSignInClick = { navController.navigate(route = Screen.SignIn.route) }
            )
        }

        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                modifier = modifier,
                onSignUp = {
                    navController.navigate(route = Screen.Home.route) {
                        popUpTo(route = Screen.Welcome.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = Screen.SignIn.route) {
            SignInScreen(
                modifier = modifier,
                onSignIn = {
                    navController.navigate(route = Screen.Home.route) {
                        popUpTo(route = Screen.Welcome.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = Screen.ResetPassword.route) {
            ResetPasswordScreen(
                modifier = modifier,
                onLinkSend = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                modifier = modifier
            )
        }
    }

}