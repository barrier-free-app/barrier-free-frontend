package com.moduro.barrier_free_app.presentation.mypage.navigation

import androidx.navigation.NavController

class MypageNavigator(
    val navController: NavController
){
    fun navigateToProfileSetting() {
        navController.navigate("profile_setting")
    }
    fun navigateToMypage() {
        navController.navigate("mypage")
    }
    fun navigateToFavoritePlace() {
        navController.navigate("favorite_place")
    }

}