package com.moduro.barrier_free_app.presentation.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.moduro.barrier_free_app.presentation.detail.screen.PlaceDetailRoute
import com.moduro.barrier_free_app.presentation.mypage.navigation.MypageNavigator
import com.moduro.barrier_free_app.presentation.mypage.screen.MypageRoute
import com.moduro.barrier_free_app.presentation.search.screen.SearchRoute

fun NavGraphBuilder.searchNavGraph(
    navigator: SearchNavigator
) {

    composable(
        route = "searchScreen?searchValue={searchValue}",
        arguments = listOf(
            navArgument("searchValue") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) { backStackEntry ->
        val searchValue = backStackEntry.arguments?.getString("searchValue")

        SearchRoute(
            navigator = navigator,
            searchValue = searchValue,
        )
    }

}