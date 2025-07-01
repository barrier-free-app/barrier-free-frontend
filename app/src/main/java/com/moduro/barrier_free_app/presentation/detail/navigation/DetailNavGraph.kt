package com.moduro.barrier_free_app.presentation.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.moduro.barrier_free_app.presentation.detail.screen.PlaceDetailRoute
import com.moduro.barrier_free_app.presentation.detail.screen.PlaceDetailScreen
import com.moduro.barrier_free_app.presentation.detail.screen.ReviewWriteScreen

fun NavGraphBuilder.detailNavGraph(
    navigator: DetailNavigator
) {
    composable(
        route = "placeDetail/{placeId}",
        arguments = listOf(navArgument("placeId") { type = NavType.IntType })
    ) { backStackEntry ->
        val placeId = backStackEntry.arguments?.getInt("placeId") ?: -1

        PlaceDetailRoute(
            placeId = placeId,
            onBackClick = { navigator.navController.popBackStack() },
            onWriteReviewClick = { navigator.navigateToReview() }
        )
    }

    composable("review") {
        ReviewWriteScreen(
            onBackClick = { navigator.navController.popBackStack() },
            onSubmitClick = { navigator.navController.popBackStack() }
        )
    }
}
