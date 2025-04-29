package com.moduro.barrier_free_app.presentation.map.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.moduro.barrier_free_app.presentation.example.navigation.ExampleNavigator
import com.moduro.barrier_free_app.presentation.map.navigation.MapNavigator


@Composable
fun MapRoute(
    navigator: MapNavigator
) {

    MapScreen()
}

@Composable
fun MapScreen() {
	Text("지도 화면입니다.")

 }


