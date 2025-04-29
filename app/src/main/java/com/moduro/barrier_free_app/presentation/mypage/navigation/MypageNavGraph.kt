package com.moduro.barrier_free_app.presentation.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moduro.barrier_free_app.presentation.example.screen.ExampleRoute
import com.moduro.barrier_free_app.presentation.mypage.screen.MypageRoute

fun NavGraphBuilder.mypageNavGraph(
    navigator: MypageNavigator
) {
    composable(route = "mypage") {
        MypageRoute(navigator = navigator)
    }
}