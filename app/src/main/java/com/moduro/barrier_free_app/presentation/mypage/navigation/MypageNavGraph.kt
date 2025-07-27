package com.moduro.barrier_free_app.presentation.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moduro.barrier_free_app.presentation.mypage.screen.FavoritePlaceScreen
import com.moduro.barrier_free_app.presentation.mypage.screen.MyReviewScreen
import com.moduro.barrier_free_app.presentation.mypage.screen.MyReviewScreenPreview
import com.moduro.barrier_free_app.presentation.mypage.screen.MypageRoute
import com.moduro.barrier_free_app.presentation.mypage.screen.PlaceReportRoute
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
    composable("favorite_place"){
        FavoritePlaceScreen(onBackClick = {navigator.navController.popBackStack()})
    }
    composable("my_review"){
        MyReviewScreen(onBackClick = {navigator.navController.popBackStack()})
    }

    composable("place_report"){
        PlaceReportRoute(navigator = navigator)
    }
}