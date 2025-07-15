package com.moduro.barrier_free_app.presentation.auth.navigation

import androidx.navigation.NavController

class AuthNavigator(
    val navController: NavController
){
    fun navigateToLogin() {
        navController.navigate("login")
    }
    fun navigateToMain() {
        navController.navigate("main") {
            navController.navigate(route = "main"){
                popUpTo(0){
                    inclusive = true
                }
            }
        }
    }
    fun navigateToSignUp() {
        navController.navigate("signup") {
        }
    }
    fun navigateToFindAccount() {
        navController.navigate("find-account") {
        }
    }
    fun navigateToSignUpSetting() {
        navController.navigate("signup-info")
    }
}