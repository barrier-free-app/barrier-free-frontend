package com.moduro.barrier_free_app.presentation.search.navigation

import androidx.navigation.NavController

class SearchNavigator(
    val navController: NavController
){

    fun navigateToPlaceDetail(placeId: Long) {
        navController.navigate("placeDetail/$placeId")
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}