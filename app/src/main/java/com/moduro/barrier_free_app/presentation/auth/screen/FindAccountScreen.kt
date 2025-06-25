package com.moduro.barrier_free_app.presentation.auth.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.presentation.auth.navigation.AuthNavigator

@Composable
fun FindAccountRoute(
    navigator: AuthNavigator
) {
    FindAccountScreen()
}

@Composable
fun FindAccountScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background2)
    ) {
        Text(
            "아이디/비밀번호 찾기 화면"
        )
    }
}

@Composable
@Preview
fun FindAccountPreview() {
    FindAccountScreen()
}