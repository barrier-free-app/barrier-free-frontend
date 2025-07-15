package com.moduro.barrier_free_app.presentation.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moduro.barrier_free_app.presentation.auth.screen.FindAccountRoute
import com.moduro.barrier_free_app.presentation.auth.screen.LoginRoute
import com.moduro.barrier_free_app.presentation.auth.screen.SignUpSettingRoute
import com.moduro.barrier_free_app.presentation.auth.screen.SignUpRoute
import com.moduro.barrier_free_app.presentation.auth.screen.StartRoute
import com.moduro.barrier_free_app.presentation.home.screen.HomeRoute

fun NavGraphBuilder.authNavGraph(
    navigator: AuthNavigator
) {
    composable(route = "start") {
        StartRoute(navigator = navigator)
    }
    composable(route = "login") {
        LoginRoute(navigator = navigator)
    }
    composable(route = "signup") {
        SignUpRoute(navigator = navigator)
    }
    composable(route = "find-account") {
        FindAccountRoute(navigator = navigator)
    }
    composable(route = "signup-info") {
        SignUpSettingRoute(navigator = navigator)
    }
}