package com.moduro.barrier_free_app.presentation.mypage.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.moduro.barrier_free_app.presentation.example.navigation.ExampleNavigator
import com.moduro.barrier_free_app.presentation.mypage.navigation.MypageNavigator


@Composable
fun MypageRoute(
    navigator: MypageNavigator
) {

    MypageScreen()
}

@Composable
fun MypageScreen() {
	Text("마이페이지입니다.")

 }


