package com.moduro.barrier_free_app.presentation.mypage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.component.ProfileFacilityChip
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider


data class FavoritePlace(
    val name: String,
    val description: String,
    val imageRes: Int
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FavoritePlaceScreen(
    onBackClick: () -> Unit
) {
    val favoritePlaces = remember {
        mutableStateListOf(
            FavoritePlace("국립현대미술관 서울 MMCA", "배리어프리 서비스 도입 미술관", R.drawable.place),
            FavoritePlace("쇼어 SHORE", "아이와 함께 가기 좋은 실내 카페", R.drawable.place)
        )
    }

    val selectedFacilities = remember { mutableStateListOf<String>() }
    val scrollState = rememberScrollState()
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Background2
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background2)
            .verticalScroll(scrollState)
    ) {
        CommonTopBar(title = "좋아하는 장소", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "전체", "🛗 승강기", "♿ 장애인화장실", "👶 영유아동반",
                "🛁 수유실", "# 경사로"
            ).forEach { label ->
                ProfileFacilityChip(
                    label = label,
                    isSelected = selectedFacilities.contains(label),
                    onClick = {
                        if (selectedFacilities.contains(label)) {
                            selectedFacilities.remove(label)
                        } else {
                            selectedFacilities.add(label)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (favoritePlaces.isEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
                    .padding(bottom = 80.dp),

                ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_favorite_place_null),
                    contentDescription = null,
                    modifier = Modifier
                        .width(160.dp)
                        .height(105.dp)
                )
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                favoritePlaces.forEach { place ->
                    FavoritePlaceCard(
                        place = place,
                        onRemoveClick = { favoritePlaces.remove(place) } // 삭제 콜백 전달
                    )
                }
            }
        }
    }
}


@Composable
fun FavoritePlaceCard(
    place: FavoritePlace,
    onRemoveClick: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current
    val isLiked = remember { mutableStateListOf(true) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        color = Background1
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = place.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = place.name,
                    style = typography.H5_SB_5,
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = painterResource(
                        id = if (isLiked[0]) R.drawable.ic_heart_filled
                        else R.drawable.ic_heart_unfilled
                    ),
                    contentDescription = "좋아요",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            isLiked[0] = false
                            onRemoveClick()
                        }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = place.description,
                style = typography.H7_M_5,
                color = Color.Gray
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun FavoritePlaceCardPreview() {
    FavoritePlaceCard(
        place = FavoritePlace(
            name = "국립현대미술관 서울 MMCA",
            description = "배리어프리 서비스 도입 미술관",
            imageRes = R.drawable.place
        ), onRemoveClick = {}
    )
}

@Composable
@Preview(showBackground = true)
fun FavoritePlaceScreenPreview() {
    FavoritePlaceScreen(
        onBackClick = {}
    )
}
