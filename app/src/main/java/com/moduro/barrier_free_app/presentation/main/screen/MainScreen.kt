package com.moduro.barrier_free_app.presentation.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.component.BottomNavigationItem
import com.moduro.barrier_free_app.core_ui.theme.Barrier_free_appTheme
import com.moduro.barrier_free_app.presentation.example.navigation.ExampleNavigator
import com.moduro.barrier_free_app.presentation.example.screen.ExampleRoute
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
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavigationItem(
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_launcher_selected),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
            label = "홈화면"
        ),
        BottomNavigationItem(
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_launcher_selected),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
            label = "지도"
        ),
        BottomNavigationItem(
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_launcher_selected),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
            label = "마이페이지"
        ),
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.background(White),
                containerColor = White
            ) {
                items.forEachIndexed { index, item ->
                    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selectedItem == index) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,

                                )
                            },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = Black,
                                unselectedTextColor = Gray,
                                indicatorColor = White
                            ),
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
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
