package com.kira.android.filipinorecipe.features.account.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.kira.android.filipinorecipe.utils.ColorUtils
import kotlinx.coroutines.flow.SharedFlow

lateinit var viewModel: LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController
) {
    viewModel = hiltViewModel()
    MainScreen(navController, viewModel.loginState)
}

@Composable
fun MainScreen(navController: NavController, sharedFlow: SharedFlow<LoginState>) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect { state ->
                when (state) {
                    is LoginState.OnLogin -> {

                    }

                    is LoginState.ShowError -> {

                    }
                }
            }
        }
    }
    PopulateLoginScreen()
}

@Composable
fun PopulateLoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = ColorUtils().recipeListBackgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .height(50.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(24.dp))
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = Color.LightGray,
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(modifier = Modifier.weight(1f)) {
                            if (email.isEmpty()) {
                                Text("Email", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            BasicTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .height(50.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(24.dp))
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Key,
                            contentDescription = null,
                            tint = Color.LightGray,
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(modifier = Modifier.weight(1f)) {
                            if (email.isEmpty()) {
                                Text("Password", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    viewModel.login(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)

            ) {
                Text("Login")
            }
        }
    }
}