package com.moduro.barrier_free_app.presentation.mypage.screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.component.CommonBottomSheet
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text3
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.domain.entity.ReviewPlaceEntity

@Composable
fun MyReviewScreen(
    onBackClick: () -> Unit,
    viewModel: MypageViewModel = hiltViewModel()
) {
    val reviewPlaces by viewModel.reviewPlaces.collectAsState()
    val scrollState = rememberScrollState()
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(color = Background2)
        viewModel.loadReviewPlaces()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background2)
            .verticalScroll(scrollState)
    ) {
        CommonTopBar(title = "내가 쓴 리뷰", onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(20.dp))

        if (reviewPlaces.isEmpty()) {
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
                reviewPlaces.forEach { reviewPlace ->
                    ReviewCard(
                        reviewPlaceEntity = reviewPlace,
                        onDetailClick = {
                            // 상세 화면으로 이동
                            // navController.navigate("review_detail/${reviewPlace.placeId}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewCard(
    reviewPlaceEntity: ReviewPlaceEntity,
    onDetailClick: () -> Unit
) {
    var showDeleteSheet by remember { mutableStateOf(false) }
    val typography = LocalbarrierFreeTypographyProvider.current

    val placeholderImageRes = when (reviewPlaceEntity.imageType) {
        0 -> R.drawable.review_placeholder_parking
        1 -> R.drawable.review_placeholder_culture
        2 -> R.drawable.review_placeholder_restaurant
        3 -> R.drawable.review_placeholder_elevator
        4 -> R.drawable.review_placeholder_nursing_room
        else -> R.drawable.review_placeholder_toilet
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (reviewPlaceEntity.reviewImageUrls.isNotEmpty()) {
                AsyncImage(
                    model = reviewPlaceEntity.reviewImageUrls.first(),
                    contentDescription = null,
                    placeholder = painterResource(id = placeholderImageRes),
                    error = painterResource(id = placeholderImageRes),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(90.dp)
                        .height(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                Image(
                    painter = painterResource(id = placeholderImageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(120.dp)
                        .height(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

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
                        text = reviewPlaceEntity.placeName,
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
                                // TODO: 실제 삭제 API 호출
                                // viewModel.deleteReview(reviewPlaceEntity.placeId)
                            },
                            showWithdrawReasons = false
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = reviewPlaceEntity.placeType,
                    style = typography.H8_SB,
                    color = Text3
                )

                Spacer(modifier = Modifier.height(10.dp))

                val ratingInt = reviewPlaceEntity.rating.toInt()
                Row {
                    repeat(ratingInt) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_star_selected),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    repeat(5 - ratingInt) {
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
            text = reviewPlaceEntity.content,
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
@Preview()
fun ReviewCardPreview() {
    ReviewCard(
        reviewPlaceEntity = ReviewPlaceEntity(
            placeId = 1L,
            placeName = "예술의전당",
            placeType = "문화시설",
            imageType = 1,
            content = "장애인 접근성이 아주 잘 되어있어요. 안내도 친절했고 휠체어 대여도 가능했습니다.",
            rating = 4.5,
            reviewImageUrls = listOf(
                "https://via.placeholder.com/150"
            )
        ),
        onDetailClick = {}
    )
}
