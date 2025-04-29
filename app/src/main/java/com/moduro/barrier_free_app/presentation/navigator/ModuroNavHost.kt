package com.moduro.barrier_free_app.presentation.navigator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moduro.barrier_free_app.presentation.auth.navigation.AuthNavigator
import com.moduro.barrier_free_app.presentation.auth.screen.SplashScreen
import com.moduro.barrier_free_app.presentation.home.navigation.HomeNavigator
import com.moduro.barrier_free_app.presentation.home.navigation.homeNavGraph
import com.moduro.barrier_free_app.presentation.main.navigation.MainNavigator
import com.moduro.barrier_free_app.presentation.main.navigation.mainNavGraph
import com.moduro.barrier_free_app.presentation.map.navigation.MapNavigator
import com.moduro.barrier_free_app.presentation.map.navigation.mapNavGraph
import com.moduro.barrier_free_app.presentation.mypage.navigation.MypageNavigator
import com.moduro.barrier_free_app.presentation.mypage.navigation.mypageNavGraph


@Composable
fun ModuroNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authNavigator: AuthNavigator,
    mainNavigator: MainNavigator,
    homeNavigator: HomeNavigator,
    mapNavigator: MapNavigator,
    mypageNavigator: MypageNavigator
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        NavHost(
            navController = navController,
            startDestination = "splash",
        ) {
            composable("splash") { SplashScreen(navController = authNavigator.navController) }

            mainNavGraph(
                mainNavigator,
                homeNavigator,
                mapNavigator,
                mypageNavigator
            )
            homeNavGraph(homeNavigator)
            mapNavGraph(mapNavigator)
            mypageNavGraph(mypageNavigator)

        }
    }
}