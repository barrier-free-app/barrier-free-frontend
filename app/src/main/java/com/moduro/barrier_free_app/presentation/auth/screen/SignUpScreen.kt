package com.moduro.barrier_free_app.presentation.auth.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.component.SignUpTextField
import com.moduro.barrier_free_app.core_ui.component.StartButton
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.presentation.auth.navigation.AuthNavigator

@Composable
fun SignUpRoute(
    navigator: AuthNavigator,
    viewModel: SignUpViewModel = viewModel()
) {
    SignUpScreen(
        onBackClick = { navigator.navController.popBackStack() },
        viewModel = viewModel,
        onSignUpSuccess = { navigator.navigateToSignUpSetting() }
    )
}

@Composable
fun SignUpScreen(
    onBackClick: () -> Unit,
    viewModel: SignUpViewModel,
    onSignUpSuccess: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background2)
            .padding(horizontal = 16.dp)
    ) {
        CommonTopBar(title = "회원가입", onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(53.dp))

        if (viewModel.currentStep == 1) {
            Text(
                "이메일을\n먼저 입력해주세요",
                style = typography.H1_SB
            )

            Spacer(modifier = Modifier.height(79.dp))

            SignUpTextField(
                label = "이메일 주소",
                hint = "아이디 또는 이메일 주소",
                text = viewModel.email,
                onValueChange = {
                    viewModel.email = it
                },
                enabled = true
            )

            Spacer(modifier = Modifier.height(330.dp))

            StartButton(
                value = "인증번호 요청하기",
                enabled = viewModel.isSignUpEnabled,
                onClick = {
                    viewModel.sendVerificationCode(
                        email = viewModel.email,
                        onSuccess = { viewModel.currentStep = 2 },
                        onFailure = { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()}
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            StartButton(
                value = "다음",
                enabled = false,
                onClick = {
                    // 인증번호 확인
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
        } else {
            Text(
                "이메일로 인증번호를 보냈어요 \n인증번호를 입력해주세요",
                style = typography.H1_SB
            )

            Spacer(modifier = Modifier.height(79.dp))

            SignUpTextField(
                label = "이메일 주소",
                hint = "아이디 또는 이메일 주소",
                text = viewModel.email,
                onValueChange = {
                    viewModel.email = it
                },
                enabled = false
            )

            Spacer(modifier = Modifier.height(10.dp))

            SignUpTextField(
                label = "인증번호 확인",
                hint = "인증번호 4자리",
                text = viewModel.verificationCode,
                onValueChange = {
                    viewModel.verificationCode = it
                },
                enabled = true
            )

            Spacer(modifier = Modifier.height(340.dp))

            StartButton(
                value = "다음",
                enabled = viewModel.isSignUpEnabled,
                onClick = {
                    //viewModel.signUp(onSignUpSuccess)
                    viewModel.verifyVerificationCode(
                        email = viewModel.email,
                        verificationCode = viewModel.verificationCode,
                        onSuccess = onSignUpSuccess,
                        onFailure = { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
@Preview
fun SignUpPreview() {
    SignUpScreen(
        onBackClick = {},
        viewModel = viewModel(),
        onSignUpSuccess = {}
    )
}