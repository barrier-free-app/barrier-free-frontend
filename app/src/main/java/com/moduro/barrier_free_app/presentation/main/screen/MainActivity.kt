package com.moduro.barrier_free_app.presentation.main.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.core_ui.theme.Barrier_free_appTheme
import com.moduro.barrier_free_app.presentation.auth.navigation.AuthNavigator
import com.moduro.barrier_free_app.presentation.auth.screen.OAuthUiState
import com.moduro.barrier_free_app.presentation.auth.screen.OAuthViewModel
import com.moduro.barrier_free_app.presentation.detail.navigation.DetailNavigator
import com.moduro.barrier_free_app.presentation.example.navigation.ExampleNavigator
import com.moduro.barrier_free_app.presentation.home.navigation.HomeNavigator
import com.moduro.barrier_free_app.presentation.main.navigation.MainNavigator
import com.moduro.barrier_free_app.presentation.map.navigation.MapNavigator
import com.moduro.barrier_free_app.presentation.mypage.navigation.MypageNavigator
import com.moduro.barrier_free_app.presentation.navigator.ModuroNavHost
import com.moduro.barrier_free_app.presentation.search.navigation.SearchNavigator
import com.moduro.barrier_free_app.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val oauthViewModel: OAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        intent?.data?.let { oauthViewModel.handleDeepLink(it) }
        setContent {
            Barrier_free_appTheme {
                val context = LocalContext.current
                var backPressedState by remember { mutableStateOf(true) }
                var backPressedTime = 0L
                val systemUiController = rememberSystemUiController()
                val lifecycleOwner = LocalLifecycleOwner.current

                BackHandler(enabled = backPressedState) {
                    if (System.currentTimeMillis() - backPressedTime <= 3000) {
                        (context as Activity).finish()
                    } else {
                        backPressedState = true
                        context.toast("한 번 더 누르면 종료돼요")
                    }
                    backPressedTime = System.currentTimeMillis()
                }

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = White
                    )
                }

                DisposableEffect(key1 = lifecycleOwner) {
                    onDispose {
                        systemUiController.setStatusBarColor(
                            color = Transparent
                        )
                    }
                }

                val navController = rememberNavController()
                val exampleNavigator = remember(navController) { ExampleNavigator(navController) }
                val authNavigator = remember(navController) { AuthNavigator(navController) }
                val mainNavigator = remember(navController) { MainNavigator(navController) }
                val homeNavigator = remember(navController) { HomeNavigator(navController) }
                val mapNavigator = remember(navController) { MapNavigator(navController) }
                val mypageNavigator = remember(navController) { MypageNavigator(navController) }
                val detailNavigator = remember(navController) { DetailNavigator(navController)}
                val searchNavigator = remember(navController) { SearchNavigator(navController) }


                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    content = { paddingValues ->
                        ModuroNavHost(
                            modifier = Modifier
                                .padding(paddingValues),
                            navController = navController,
                            authNavigator = authNavigator,
                            mainNavigator = mainNavigator,
                            homeNavigator = homeNavigator,
                            mapNavigator = mapNavigator,
                            mypageNavigator = mypageNavigator,
                            detailNavigator = detailNavigator,
                            searchNavigator = searchNavigator
                                                    )
                    }
                )
                val uiState by oauthViewModel.uiState.collectAsState()
                LaunchedEffect(uiState) {
                    if (uiState is OAuthUiState.Success) {
                        navController.navigate("main") {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { oauthViewModel.handleDeepLink(it) }
    }
}