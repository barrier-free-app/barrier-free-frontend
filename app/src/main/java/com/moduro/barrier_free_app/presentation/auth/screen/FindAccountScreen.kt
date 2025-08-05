package com.moduro.barrier_free_app.presentation.auth.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.component.SignUpTextField
import com.moduro.barrier_free_app.core_ui.component.StartButton
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.MainYellow
import com.moduro.barrier_free_app.core_ui.theme.Text3
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.presentation.auth.navigation.AuthNavigator

@Composable
fun FindAccountRoute(
    navigator: AuthNavigator,
    viewModel: FindAccountViewModel = hiltViewModel()
) {
    FindAccountScreen(
        onBackClick = { navigator.navController.popBackStack() },
        viewModel = viewModel,
        onNavigateToLogin = { navigator.navigateToLogin() }
    )
}

@Composable
fun FindAccountScreen(
    onBackClick: () -> Unit,
    viewModel: FindAccountViewModel,
    onNavigateToLogin: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background2)
            .padding(horizontal = 16.dp)
    ) {
        CommonTopBar(title = "아이디/비밀번호 찾기", onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = when (viewModel.selectedMode) {
                FindMode.ID -> "이메일 주소를 입력해 주시면,\n아이디를 전송해 드릴게요"
                FindMode.PASSWORD -> "이메일 주소를 입력해 주시면,\n비밀번호를 전송해 드릴게요"
            },
            style = typography.H1_SB
        )

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        ) {
            Button(
                modifier = Modifier
                    .width(180.dp)
                    .height(45.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = { viewModel.switchToId() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (viewModel.selectedMode == FindMode.ID) MainYellow else Button1,
                    contentColor = if (viewModel.selectedMode == FindMode.ID) Text4 else Text3
                )
            ) {
                Text("아이디", style = typography.H5_M_10)
            }
            Button(
                modifier = Modifier
                    .width(180.dp)
                    .height(45.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = { viewModel.switchToPW() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (viewModel.selectedMode == FindMode.PASSWORD) MainYellow else Button1,
                    contentColor = if (viewModel.selectedMode == FindMode.PASSWORD) Text4 else Text3
                )
            ) {
                Text("비밀번호", style = typography.H5_M_10)
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        SignUpTextField(
            label = if (viewModel.selectedMode == FindMode.ID) "아이디 찾기" else "비밀번호 찾기",
            hint = "이메일 주소를 입력해 주세요",
            text = viewModel.email,
            onValueChange = {
                viewModel.email = it
            },
            enabled = true
        )

        Spacer(modifier = Modifier.height(350.dp))

        StartButton(
            value = "전송하기",
            enabled = viewModel.isEmailEntered,
            onClick = {
                viewModel.onSubmit {

                    Toast.makeText(context,"전송이 완료되었어요!", Toast.LENGTH_SHORT).show()
                    onNavigateToLogin()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )
    }
}