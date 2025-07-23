package com.moduro.barrier_free_app.presentation.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moduro.barrier_free_app.presentation.mypage.navigation.MypageNavigator
import com.moduro.barrier_free_app.presentation.mypage.screen.MypageRoute
import com.moduro.barrier_free_app.presentation.search.screen.SearchRoute

fun NavGraphBuilder.searchNavGraph(
    navigator: SearchNavigator
) {
    composable(route = "search") {
        SearchRoute(navigator = navigator)
    }
}