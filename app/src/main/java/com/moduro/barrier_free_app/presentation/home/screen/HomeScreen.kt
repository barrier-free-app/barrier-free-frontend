package com.moduro.barrier_free_app.presentation.home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.component.HomeWeatherBox
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.presentation.home.navigation.HomeNavigator


@Composable
fun HomeRoute(
    navigator: HomeNavigator
) {

    HomeScreen()
}

@Composable
fun HomeScreen() {
	Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background2)
            .padding(horizontal = 25.dp)
            .padding(top = 17.dp)

    ) {
        val typography = LocalbarrierFreeTypographyProvider.current

        Row(){
            Image(
                painter = painterResource(id = R.drawable.moduro_logo),
                contentDescription = "",
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
            ) {
                Image(
                    painter = painterResource(id = R.drawable.large_word_selected),
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text("큰 글자 모드", style = typography.H4_SB, color = Text4)
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        //HomeWeatherBox(1,2,"서울특별시 용산구", "20")

        Spacer(modifier = Modifier.height(30.dp))

        Row(){
            Text("추천 플레이스", style = typography.H4_SB, color = Text4)

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.home_filter),
                contentDescription = "",
                modifier = Modifier.height(22.dp).width(22.dp)
            )

        }

        Spacer(modifier = Modifier.height(13.dp))

        Text("HOT 인기 플레이스를 추천해 드릴게요.", style = typography.H9_M, color = Text4)



    }

 }


@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen()
}