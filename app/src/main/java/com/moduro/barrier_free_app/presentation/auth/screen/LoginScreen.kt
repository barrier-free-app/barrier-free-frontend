package com.moduro.barrier_free_app.presentation.auth.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.presentation.auth.navigation.AuthNavigator

@Composable
fun LoginRoute(
    navigator: AuthNavigator
) {

}

@Composable
fun LoginScreen(
    onBackClick: () -> Unit
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


    }
}

@Composable
@Preview
fun LoginPreview() {
    LoginScreen(
        onBackClick = {}
    )
}