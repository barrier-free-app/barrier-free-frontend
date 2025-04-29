package com.moduro.barrier_free_app.presentation.home.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.moduro.barrier_free_app.presentation.home.navigation.HomeNavigator


@Composable
fun HomeRoute(
    navigator: HomeNavigator
) {

    HomeScreen()
}

@Composable
fun HomeScreen() {
	Text("홈화면입니다.")

 }


