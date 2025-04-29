package com.moduro.barrier_free_app.presentation.map.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moduro.barrier_free_app.presentation.example.screen.ExampleRoute
import com.moduro.barrier_free_app.presentation.map.screen.MapRoute

fun NavGraphBuilder.mapNavGraph(
    navigator: MapNavigator
) {
    composable(route = "map") {
        MapRoute(navigator = navigator)
    }
}