package com.moduro.barrier_free_app.presentation.mypage.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.component.FacilityChip
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.MainYellow
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.core_ui.theme.Text5
import com.moduro.barrier_free_app.presentation.mypage.navigation.MypageNavigator


@Composable
fun MypageRoute(
    navigator: MypageNavigator,
    viewModel: MypageViewModel = hiltViewModel()
) {

    val userInfo = viewModel.userInfo.collectAsState().value

    userInfo?.let { user ->
        MypageScreen(
            userName = user.nickName,
            userEmail = user.email,
            userFacilities = user.userFacilities,
            onLogoutClick = { viewModel.onLogoutClick() },
            onReviewClick = { viewModel.onReviewClick() },
            onFavoritePlaceClick = { viewModel.onFavoritePlaceClick() },
            onEditProfileClick = { viewModel.onEditProfileClick() },
            onReportPlaceClick = { viewModel.onReportPlaceClick() }
        )
    }

}

@Composable
fun MypageScreen(
    userName: String = "버블티먹는코끼리",
    userEmail: String = "aaa@naver.com",
    userFacilities: List<Int> = listOf(1, 3, 4),
    onLogoutClick: () -> Unit = {},
    onReviewClick: () -> Unit = {},
    onFavoritePlaceClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onReportPlaceClick: () -> Unit = {}
) {
   
    val typography = LocalbarrierFreeTypographyProvider.current
    val scrollState = rememberScrollState()
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(color = Background2)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background2)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_moduro),
                contentDescription = "로고",
                modifier = Modifier.size(40.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onReportPlaceClick() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_megaphone),
                    contentDescription = "장소 제보",
                    modifier = Modifier.size(37.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "장소 제보",
                    style = typography.H3_B,
                    color = Text5
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "마이프로필",
            style = typography.H4_SB
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MainYellow,
            modifier = Modifier
                .fillMaxWidth()
                .height(176.dp)
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                Column {
                    Text(
                        text = "프로필 카드",
                        style = typography.H9_M,
                        color = Text4
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = "프로필 이미지",
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            Text(
                                text = userName,
                                style = typography.H4_SB
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = userEmail,
                                style = typography.H6_M,
                                color = Color.DarkGray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val facilityLabels = mapOf(
                        1 to "전체",
                        2 to "영유아동반",
                        3 to "승강기",
                        4 to "장애인 화장실",
                        5 to "주차장"
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(start = 70.dp)
                    ) {
                        items(userFacilities) { facilityId ->
                            FacilityChip(label = facilityLabels[facilityId] ?: "기타")
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable { onEditProfileClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_pencil),
                        contentDescription = "수정 아이콘",
                        modifier = Modifier.size(19.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "내용 수정하기",
                        style = typography.H9_M,
                        color = Text4
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "메뉴",
            style = typography.H4_SB,
            color = Text5
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MypageMenuItem(icon = R.drawable.ic_heart_filled, label = "좋아하는 장소", onClick = onFavoritePlaceClick)
            MypageMenuItem(icon = R.drawable.ic_talk, label = "내가 쓴 리뷰", onClick = onReviewClick)
            MypageMenuItem(icon = R.drawable.ic_logout, label = "로그아웃", onClick = onLogoutClick)
            MypageMenuItem(icon = R.drawable.ic_question_mark, label = "도움말")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "회원을 탈퇴하시겠어요?",
            style = typography.H7_M_10,
            color = Color.Gray
        )
    }
}


@Composable
fun MypageMenuItem(
    @DrawableRes icon: Int,
    label: String,
    onClick: () -> Unit = {}
) {
    val typography = LocalbarrierFreeTypographyProvider.current
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(67.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                style = typography.H6_M

            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 375, heightDp = 812)
@Composable
fun MypageScreenPreview() {
    MypageScreen(
        userName = "버블티먹는코끼리",
        userEmail = "aaa@naver.com"
    )
}
