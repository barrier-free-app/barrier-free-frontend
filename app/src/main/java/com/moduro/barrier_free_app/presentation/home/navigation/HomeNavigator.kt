package com.moduro.barrier_free_app.presentation.home.navigation

import androidx.navigation.NavController

class HomeNavigator(
    val navController: NavController
){
    fun navigateToPlaceDetail(placeId: Int) {
        navController.navigate("placeDetail/$placeId")
    }
}