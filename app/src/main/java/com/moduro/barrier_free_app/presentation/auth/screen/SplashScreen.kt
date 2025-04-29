package com.moduro.barrier_free_app.presentation.auth.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, modifier: Modifier = Modifier){

    LaunchedEffect(Unit) {
        delay(2500) // 2.5초 대기
        navController.navigate("main") {
            popUpTo("splash") {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    Text("스플래시 화면입니다.")
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    SplashScreen(
        navController = rememberNavController(),
        modifier = Modifier.fillMaxSize()
    )
}

