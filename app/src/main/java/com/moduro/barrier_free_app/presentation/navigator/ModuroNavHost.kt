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
import com.moduro.barrier_free_app.presentation.auth.navigation.authNavGraph
import com.moduro.barrier_free_app.presentation.auth.screen.SplashScreen
import com.moduro.barrier_free_app.presentation.auth.screen.StartRoute
import com.moduro.barrier_free_app.presentation.detail.navigation.DetailNavigator
import com.moduro.barrier_free_app.presentation.detail.navigation.detailNavGraph
import com.moduro.barrier_free_app.presentation.detail.screen.PlaceDetailScreen
import com.moduro.barrier_free_app.presentation.detail.screen.ReviewWriteScreen
import com.moduro.barrier_free_app.presentation.home.navigation.HomeNavigator
import com.moduro.barrier_free_app.presentation.home.navigation.homeNavGraph
import com.moduro.barrier_free_app.presentation.main.navigation.MainNavigator
import com.moduro.barrier_free_app.presentation.main.navigation.mainNavGraph
import com.moduro.barrier_free_app.presentation.map.navigation.MapNavigator
import com.moduro.barrier_free_app.presentation.map.navigation.mapNavGraph
import com.moduro.barrier_free_app.presentation.mypage.navigation.MypageNavigator
import com.moduro.barrier_free_app.presentation.mypage.navigation.mypageNavGraph
import com.moduro.barrier_free_app.presentation.mypage.screen.MypageScreen


@Composable
fun ModuroNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authNavigator: AuthNavigator,
    mainNavigator: MainNavigator,
    homeNavigator: HomeNavigator,
    mapNavigator: MapNavigator,
    mypageNavigator: MypageNavigator,
    detailNavigator: DetailNavigator
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
            composable("placeDetail") { PlaceDetailScreen(onBackClick = {}, onWriteReviewClick = {})}
            composable("review") { ReviewWriteScreen(onBackClick = {}, onSubmitClick = {}) }

            composable("my") { MypageScreen() }
            mainNavGraph(
                mainNavigator,
                homeNavigator,
                mapNavigator,
                mypageNavigator,
                detailNavigator
            )
            homeNavGraph(homeNavigator)
            mapNavGraph(mapNavigator)
            mypageNavGraph(mypageNavigator)
            detailNavGraph(detailNavigator)
            authNavGraph(authNavigator)
        }
    }
}