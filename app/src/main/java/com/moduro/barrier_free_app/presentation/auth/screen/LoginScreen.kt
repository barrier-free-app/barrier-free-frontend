package com.moduro.barrier_free_app.presentation.auth.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.component.LoginTextField
import com.moduro.barrier_free_app.core_ui.component.StartButton
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text3
import com.moduro.barrier_free_app.core_ui.theme.Warning
import com.moduro.barrier_free_app.presentation.auth.navigation.AuthNavigator

@Composable
fun LoginRoute(
    navigator: AuthNavigator,
    viewModel: LoginViewModel = viewModel(),
) {
    LoginScreen(
        viewModel = viewModel,
        onBackClick = { navigator.navController.popBackStack() },
        onLoginSuccess = { navigator.navigateToMain() },
        onSignUpClick = { navigator.navigateToSignUp() },
        onFindAccountClick = { navigator.navigateToFindAccount() }
    )
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onBackClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onFindAccountClick: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background2)
            .padding(horizontal = 16.dp)
    ) {
        CommonTopBar(title = "로그인", onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(53.dp))

        Text(
            "이메일로\n로그인 중이에요",
            style = typography.H1_SB
        )
        
        Spacer(modifier = Modifier.height(79.dp))

        LoginTextField(
            label = "이메일 주소",
            hint = "아이디 또는 이메일 주소",
            text = viewModel.email,
            onValueChange = {
                viewModel.email = it
                viewModel.emailError = null
            },
            isError = viewModel.emailError != null,
            errorMessage = viewModel.emailError
        )

        if (viewModel.emailError != null) {
            Text(
                text = viewModel.emailError!!,
                color = Warning,
                style = typography.H7_M_5,
                modifier = Modifier.padding(top = 7.dp, start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LoginTextField(
            label = "비밀번호",
            hint = "비밀번호(영문+숫자 6~16자)",
            text = viewModel.password,
            onValueChange = {
                viewModel.password = it
                viewModel.passwordError = null
            },
            isError = viewModel.passwordError != null,
            errorMessage = viewModel.passwordError,
            isPassword = true
        )

        if (viewModel.passwordError != null) {
            Text(
                text = viewModel.passwordError!!,
                color = Warning,
                style = typography.H7_M_5,
                modifier = Modifier.padding(top = 7.dp, start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(230.dp))
        StartButton(
            value = "로그인",
            enabled = viewModel.isLoginEnabled,
            onClick = { viewModel.login(onLoginSuccess) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "이메일로 회원가입",
                modifier = Modifier.clickable { onSignUpClick() },
                color = Text3,
                style = typography.H7_M_5
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "아이디/비밀번호 찾기",
                modifier = Modifier.clickable { onFindAccountClick() },
                color = Text3,
                style = typography.H7_M_5
            )
        }
    }
}

@Composable
@Preview
fun LoginPreview() {
    val viewModel = remember {
        LoginViewModel().apply {
            email = ""
            password = ""
            emailError = "존재하지 않는 회원입니다."
            passwordError = "비밀번호 오류입니다.\n5번 제한시 비밀번호 찾기가 필요합니다.(1/5)"
        }
    }

    LoginScreen(
        viewModel = viewModel,
        onBackClick = {},
        onLoginSuccess = {},
        onSignUpClick = {},
        onFindAccountClick = {}
    )
}