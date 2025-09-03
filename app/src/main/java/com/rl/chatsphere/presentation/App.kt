package com.rl.chatsphere.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rl.chatsphere.R
import com.rl.chatsphere.presentation.navigation.AppNavGraph
import com.rl.chatsphere.presentation.navigation.Screen
import com.rl.chatsphere.presentation.state.AuthState
import com.rl.chatsphere.presentation.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {

    val authViewModel: AuthViewModel = hiltViewModel()
    val userState by authViewModel.userState.collectAsState()

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val title = when(currentRoute) {
        Screen.SignUp.route -> stringResource(id = R.string.create_account)
        Screen.SignIn.route -> stringResource(id = R.string.sign_in)
        Screen.ResetPassword.route -> stringResource(id = R.string.reset_password)
        Screen.Home.route -> stringResource(id = R.string.app_name)
        else -> ""
    }

    var showMenu by remember { mutableStateOf(false) }

    val signOutState by authViewModel.signOutState.collectAsState()
    LaunchedEffect(signOutState) {
        if (signOutState is AuthState.Unauthenticated) {
            navController.navigate(route = Screen.SignIn.route) {
                popUpTo(route = Screen.Home.route) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if(currentRoute != Screen.Welcome.route) {
                TopAppBar(
                    title = {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        if (currentRoute != Screen.Home.route) {
                            IconButton(
                                onClick = { navController.popBackStack() }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(id = R.string.back)
                                )
                            }
                        }
                    },
                    actions = {
                        if (currentRoute == Screen.Home.route) {
                            IconButton(
                                onClick = {
                                    showMenu = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = stringResource(id = R.string.more)
                                )
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = {
                                    showMenu = false
                                }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.sign_out)
                                        )
                                    },
                                    onClick = {
                                        showMenu = false
                                        authViewModel.signOut()
                                    }
                                )
                            }
                        }
                    }
                )
            }

        }
    ) { innerPadding ->
        AppNavGraph(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            isAuthenticated = userState is AuthState.Authenticated
        )

    }
}