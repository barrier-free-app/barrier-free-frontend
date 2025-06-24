package com.moduro.barrier_free_app.presentation.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text5
import com.moduro.barrier_free_app.presentation.auth.navigation.AuthNavigator

@Composable
fun StartRoute(
    navigator: AuthNavigator
) {
    StartScreen(
        onNavigateToLogin = {
            navigator.navigateToLogin()
        }
    )
}

@Composable
fun StartScreen(
    onNavigateToLogin: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(124.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_start_mini_logo),
            contentDescription = "moduro logo"
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_start_image),
                contentDescription = "moduro image",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                "모두에게 연결되는 길, \n누구에게나 이지모드로",
                style = typography.H1_SB,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 50.dp),
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = { onNavigateToLogin() },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Text5,
                contentColor = Background1
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text("이메일로 로그인", style = typography.H4_SB)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = Color.Gray
            )

            Text(
                text = "간편 로그인",
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Color.Black
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = Color.Gray
            )
        }
    }

}

@Preview
@Composable
fun StartPreview() {
    StartScreen(
        onNavigateToLogin = {}
    )
}