package com.rl.chatsphere.presentation.screen.resetpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rl.chatsphere.R
import com.rl.chatsphere.presentation.component.OutlinedInputField
import com.rl.chatsphere.presentation.state.ResetPasswordState

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    onLinkSend: () -> Unit
) {
    val resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
    val resetPasswordState by resetPasswordViewModel.resetPasswordState.collectAsState()

    LaunchedEffect(resetPasswordState) {
        if (resetPasswordState is ResetPasswordState.ResetPasswordLinkSent) {
            onLinkSend()
        }
    }

    var email by rememberSaveable { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedInputField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = email.isEmpty()
                },
                placeholder = stringResource(id = R.string.email),
                isError = emailError,
                errorMessage = if (emailError) "Email can't be empty" else null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            if (resetPasswordState is ResetPasswordState.Loading) {
                CircularProgressIndicator()
            }

            if (resetPasswordState is ResetPasswordState.Error) {
                (resetPasswordState as ResetPasswordState.Error).message?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }

        Button(
            onClick = {
                resetPasswordViewModel.sendResetPasswordLink(email)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(vertical = 12.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.send),
                fontWeight = FontWeight.Bold
            )
        }
    }
}