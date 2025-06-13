package com.moduro.barrier_free_app.presentation.detail.navigation

import androidx.navigation.NavController

class DetailNavigator(
    val navController: NavController
) {
    fun navigateToReview() {
        navController.navigate("review")
    }
}