package com.moduro.barrier_free_app.presentation.map.navigation

import androidx.navigation.NavController

class MapNavigator(
    val navController: NavController
){
    fun navigateToPlaceDetail(placeId: Int) {
        navController.navigate("placeDetail/$placeId")
    }

    fun navigateToSearch(searchValue : String?) {
        val value = searchValue ?: ""
        navController.navigate("searchScreen?searchValue=$value")
    }


    fun navigateBack() {
        navController.popBackStack()
    }


}