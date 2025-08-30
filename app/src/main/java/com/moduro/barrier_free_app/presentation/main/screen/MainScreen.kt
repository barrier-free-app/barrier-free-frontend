package com.moduro.barrier_free_app.presentation.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.component.BottomNavigationItem
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.presentation.home.navigation.HomeNavigator
import com.moduro.barrier_free_app.presentation.home.screen.HomeRoute
import com.moduro.barrier_free_app.presentation.main.navigation.MainNavigator
import com.moduro.barrier_free_app.presentation.map.navigation.MapNavigator
import com.moduro.barrier_free_app.presentation.map.screen.MapRoute
import com.moduro.barrier_free_app.presentation.mypage.navigation.MypageNavigator
import com.moduro.barrier_free_app.presentation.mypage.screen.MypageRoute


@Composable
fun MainRoute(
    navigator: MainNavigator,
) {
    MainScreen(
        navController = navigator.navController,
    )
}

@Composable
fun MainScreen(
    navController: NavHostController,
) {
    val typography = LocalbarrierFreeTypographyProvider.current
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavigationItem(
            selectedIcon = painterResource(id = R.drawable.nav_home_selected),
            unselectedIcon = painterResource(id = R.drawable.nav_home_unselected),
            label = "홈 화면"
        ),
        BottomNavigationItem(
            selectedIcon = painterResource(id = R.drawable.nav_map_selected),
            unselectedIcon = painterResource(id = R.drawable.nav_map_unselected),
            label = "지도 보기"
        ),
        BottomNavigationItem(
            selectedIcon = painterResource(id = R.drawable.nav_mypage_selected),
            unselectedIcon = painterResource(id = R.drawable.nav_mypage_unselected),
            label = "마이 페이지"
        ),
    )

    Scaffold(
        bottomBar = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                NavigationBar(
                    modifier = Modifier.background(White),
                    containerColor = White
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = if (selectedItem == index) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.height(29.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    style = typography.H9_M,
                                )
                            },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = Black,
                                unselectedTextColor = Text4,
                                indicatorColor = Color.Transparent
                            ),
                        )
                    }
                }
            }
        }
    )
    { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> {
                    HomeRoute(navigator = HomeNavigator(navController = navController))
                }

                1 -> {
                    MapRoute(navigator = MapNavigator(navController = navController))

                }

                2 -> {
                    MypageRoute(navigator = MypageNavigator(navController = navController))
                }
            }
        }

    }
}

// 눌러질때의 ripple 제거
private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha {
        return RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
    }
}
