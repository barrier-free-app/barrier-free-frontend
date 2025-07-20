package com.moduro.barrier_free_app.presentation.map.navigation

import androidx.navigation.NavController

class MapNavigator(
    val navController: NavController
){
    fun navigateToPlaceDetail(placeId: Int) {
        navController.navigate("placeDetail/$placeId")
    }

    fun navigateToSearch() {
        navController.navigate("search")
    }

    fun navigateBack() {
        navController.popBackStack()
    }


}