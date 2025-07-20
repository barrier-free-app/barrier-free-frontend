package com.moduro.barrier_free_app.presentation.search.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.moduro.barrier_free_app.presentation.mypage.navigation.MypageNavigator
import com.moduro.barrier_free_app.presentation.mypage.screen.MypageViewModel
import com.moduro.barrier_free_app.presentation.search.navigation.SearchNavigator

@Composable
fun SearchRoute(
    navigator: SearchNavigator,
) {

    SearchScreen()

}

@Composable
fun SearchScreen(

) {
    Text("검색 화면입니다.")
}