package com.moduro.barrier_free_app.presentation.auth.navigation

import androidx.navigation.NavController

class AuthNavigator(
    val navController: NavController
){
    fun navigateToLogin() {
        navController.navigate("login")
    }
    fun navigateToHome() {
        navController.navigate("home") {
            popUpTo("login") { inclusive = true }
        }
    }
    fun navigateToSignUp() {
        navController.navigate("signup") {
            popUpTo("login") { inclusive = true }
        }
    }
    fun navigateToFindAccount() {
        navController.navigate("find-account") {
            popUpTo("login") { inclusive = true }
        }
    }
}