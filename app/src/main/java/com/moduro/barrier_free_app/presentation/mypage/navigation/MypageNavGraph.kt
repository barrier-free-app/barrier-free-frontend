package com.moduro.barrier_free_app.presentation.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moduro.barrier_free_app.presentation.mypage.screen.MypageRoute
import com.moduro.barrier_free_app.presentation.mypage.screen.ProfileSettingScreen

fun NavGraphBuilder.mypageNavGraph(
    navigator: MypageNavigator
) {
    composable(route = "mypage") {
        MypageRoute(navigator = navigator)
    }
    composable("profile_setting") {
        ProfileSettingScreen(
            isEmailUser = true,
            onBackClick = { navigator.navController.popBackStack() },
            onNavigateToMypage = { navigator.navigateToMypage() }
        )
    }
}