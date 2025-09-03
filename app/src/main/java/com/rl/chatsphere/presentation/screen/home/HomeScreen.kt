package com.rl.chatsphere.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rl.chatsphere.R

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val messages by homeViewModel.messages.collectAsState()

    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween

    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f),
            reverseLayout = true
        ){
            items(messages.reversed()) { message ->
                MessageContent(
                    modifier = Modifier.fillMaxWidth(),
                    text = message.text,
                    isUser = message.isUser
                )
            }
        }

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.ask_anything)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (text.isNotEmpty()) {
                        homeViewModel.sendMessage(text)
                        text = ""
                    }
                }
            ),
            singleLine = true
        )


    }
}

@Composable
fun MessageContent(
    modifier: Modifier = Modifier,
    text: String,
    isUser: Boolean
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .align(alignment = if (isUser) Alignment.BottomEnd else Alignment.BottomStart)
                .padding(vertical = 8.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = if (isUser) 10.dp else 0.dp,
                        bottomEnd = if (isUser) 0.dp else 10.dp
                    )
                )
                .background(if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer)
                .padding(16.dp)
        ) {
            Text(
                text = text,
                fontWeight = if (isUser) FontWeight.Bold else FontWeight.Medium,
                color = if (isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
        }
    }

}