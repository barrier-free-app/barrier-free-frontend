package com.moduro.barrier_free_app.presentation.example.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moduro.barrier_free_app.presentation.example.screen.ExampleRoute

fun NavGraphBuilder.exampleNavGraph(
    navigator: ExampleNavigator
) {
    composable(route = "example") {
        ExampleRoute(navigator = navigator)
    }
}