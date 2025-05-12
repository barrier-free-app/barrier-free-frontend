package com.moduro.barrier_free_app.presentation.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.MainYellow
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

    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainYellow)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_start_logo),
                contentDescription = "moduro logo"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    SplashScreen(
        navController = rememberNavController(),
        modifier = Modifier.fillMaxSize()
    )
}

