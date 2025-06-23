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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.component.CommonBottomSheet
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.component.FavoriteFacilityChip
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text3
import com.moduro.barrier_free_app.core_ui.theme.Text4


data class ReviewPlace(
    val placeName: String,
    val subDescription: String,
    val reviewText: String,
    val rating: Int,
    val userName: String,
    val imageRes: Int,
    val categories: List<String>,
    val onDetailClick: () -> Unit
)


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyReviewScreen(
    onBackClick: () -> Unit
) {
    val reviewPlaces = remember {
        mutableStateListOf(
            ReviewPlace(
                placeName = "국립현대미술관 서울 MMCA",
                subDescription = "배리어프리 서비스 도입 미술관",
                reviewText = "시설이 전반적으로 이동하는 데 어려움이 크진 않아 좋았던 것 같아요. 다른 미술관보다 배리어프리 신경 쓴 게 더 느껴지는 것 같네요!",
                rating = 5,
                userName = "나현",
                imageRes = R.drawable.place,
                categories = listOf("🛗 승강기", "♿ 장애인화장실"),
                onDetailClick = {}
            ),
            ReviewPlace(
                placeName = "쇼어 SHORE",
                subDescription = "아이와 함께 가기 좋은 실내 카페",
                reviewText = "카페가 크고 내부에 엘리베이터도 있어 이동하기 굉장히 편합니다! 다만 거리가 좀 있다는 게 아쉽네요.",
                rating = 4,
                userName = "나현",
                imageRes = R.drawable.place,
                categories = listOf("👶 영유아동반", "🛁 수유실"),
                onDetailClick = {}
            )
        )
    }

    val selectedFacilities = remember { mutableStateListOf<String>() }
    val scrollState = rememberScrollState()
    val systemUiController = rememberSystemUiController()

    val filteredPlaces = if (selectedFacilities.isEmpty() || selectedFacilities.contains("전체")) {
        reviewPlaces
    } else {
        reviewPlaces.filter { place ->
            place.categories.any { it in selectedFacilities }
        }
    }

    SideEffect {
        systemUiController.setSystemBarsColor(color = Background2)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background2)
            .verticalScroll(scrollState)
    ) {
        CommonTopBar(title = "내가 쓴 리뷰", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "전체", "🛗 승강기", "♿ 장애인화장실", "👶 영유아동반",
                "🛁 수유실", "🧑‍🦽 경사로"
            ).forEach { label ->
                FavoriteFacilityChip(
                    label = label,
                    isSelected = selectedFacilities.contains(label) ||
                            (selectedFacilities.contains("전체") && label != "전체")
                    ,
                    onClick = {
                        if (label == "전체") {
                            selectedFacilities.clear()
                            selectedFacilities.add("전체")
                        } else {
                            if (selectedFacilities.contains("전체")) {
                                selectedFacilities.remove("전체")
                            }

                            if (selectedFacilities.contains(label)) {
                                selectedFacilities.remove(label)
                            } else {
                                selectedFacilities.add(label)
                            }
                        }
                    }

                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredPlaces.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 150.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_my_review_null),
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
                filteredPlaces.forEach { place ->
                    ReviewCard(
                        placeName = place.placeName,
                        subDescription = place.subDescription,
                        reviewText = place.reviewText,
                        rating = place.rating,
                        imageRes = place.imageRes,
                        onDetailClick = place.onDetailClick
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewCard(
    placeName: String,
    subDescription: String,
    reviewText: String,
    rating: Int,
    imageRes: Int,
    onDetailClick: () -> Unit
) {
    var showDeleteSheet by remember { mutableStateOf(false) }
    val typography = LocalbarrierFreeTypographyProvider.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = placeName,
                        style = typography.H7_M_5,
                        color = Text4,
                        modifier = Modifier.weight(1f)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_more_vert),
                        contentDescription = "More Options",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { showDeleteSheet = true }
                    )
                    // 삭제시
                    if (showDeleteSheet) {
                        CommonBottomSheet(
                            showSheet = showDeleteSheet,
                            onDismissRequest = { showDeleteSheet = false },
                            title = "해당 리뷰를 삭제 하시겠어요?",
                            description = "삭제된 리뷰는 복구할 수 없어요!",
                            cancelText = "취소",
                            confirmText = "삭제",
                            onCancel = { showDeleteSheet = false },
                            onConfirm = {
                                showDeleteSheet = false
                            },
                            showWithdrawReasons = false
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = subDescription,
                    style = typography.H8_SB,
                    color = Text3
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    repeat(rating) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_star_selected),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    repeat(5 - rating) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_star_unselected),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = reviewText,
            style = typography.H8_SB,
            color = Text4,
            maxLines = 3,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onDetailClick() }
                .background(
                    color = Button1,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "자세히 보기",
                style = typography.H9_M,
                color = Text4
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MyReviewScreenPreview() {
    MyReviewScreen(
        onBackClick = {}
    )
}
