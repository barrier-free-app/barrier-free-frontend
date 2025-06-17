package com.moduro.barrier_free_app.presentation.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moduro.barrier_free_app.presentation.detail.screen.PlaceDetailScreen
import com.moduro.barrier_free_app.presentation.detail.screen.ReviewWriteScreen

fun NavGraphBuilder.detailNavGraph(
    navigator: DetailNavigator
) {
    composable("placeDetail") {
        PlaceDetailScreen(
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
