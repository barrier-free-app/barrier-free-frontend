package com.moduro.barrier_free_app.presentation.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moduro.barrier_free_app.presentation.main.screen.MainRoute
import com.moduro.barrier_free_app.presentation.example.navigation.ExampleNavigator
import com.moduro.barrier_free_app.presentation.home.navigation.HomeNavigator
import com.moduro.barrier_free_app.presentation.map.navigation.MapNavigator
import com.moduro.barrier_free_app.presentation.mypage.navigation.MypageNavigator

fun NavGraphBuilder.mainNavGraph(
    mainNavigator: MainNavigator,
    homeNavigator: HomeNavigator,
    mapNavigator: MapNavigator,
    mypageNavigator: MypageNavigator
) {
    composable(route = "main") {
        MainRoute(navigator = mainNavigator)
    }
}