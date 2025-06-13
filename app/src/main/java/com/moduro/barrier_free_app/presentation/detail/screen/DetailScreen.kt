package com.moduro.barrier_free_app.presentation.detail.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.component.FacilityChip
import com.moduro.barrier_free_app.core_ui.component.RecommendModal
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.SubYellow
import com.moduro.barrier_free_app.core_ui.theme.Text3
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.core_ui.theme.Text5


@Composable
fun PlaceDetailScreen(
    onBackClick: () -> Unit,
    isReportedByUser: Boolean = true,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val placeDetail = viewModel.placeDetail.collectAsState().value
    val visibleReviewsCount by viewModel.visibleReviewsCount.collectAsState()
    val recommendState = viewModel.recommendType.collectAsState().value


    if (placeDetail == null) {
        Text(text = "로딩중...")
        return
    }

    val typography = LocalbarrierFreeTypographyProvider.current
    val scrollState = rememberScrollState()
    val backgroundColor = if (isReportedByUser) SubYellow else Background2
    val systemUiController = rememberSystemUiController()


    SideEffect {
        systemUiController.setSystemBarsColor(
            color = backgroundColor
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(bottom = 113.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonTopBar(title = "플레이스 상세 정보", onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.place),
            contentDescription = null,
            modifier = Modifier
                .width(212.dp)
                .height(166.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = placeDetail.name, style = typography.H4_SB)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "배리어프리 서비스 도입 미술관", style = typography.H6_M, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier
                .width(380.dp)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row {
                    Column {
                        Text(text = "주소", style = typography.H9_M)
                        Text(text = placeDetail.address, style = typography.H5_SB_5)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = if (placeDetail.isFavorite) R.drawable.ic_heart_unfilled else R.drawable.ic_heart_filled),
                        contentDescription = "heart",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                viewModel.toggleFavorite()
                            }
                    )

                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = "영업시간", style = typography.H9_M)
                Text(text = placeDetail.openingHours, style = typography.H5_SB_5)

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = "편의시설 목록", style = typography.H9_M)

                val facilityLabels = mapOf(
                    1 to "🛗 승강기",
                    3 to "♿ 장애인화장실",
                    5 to "👶 영유아동반"
                )

                LazyRow(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(placeDetail.facilities) { facilityId ->
                        FacilityChip(facilityLabels[facilityId] ?: "알 수 없음")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "리뷰 ${placeDetail.reviewCount}", style = typography.H4_SB)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${placeDetail.averageRating}", style = typography.H4_SB)
            Spacer(modifier = Modifier.width(4.dp))
            repeat(placeDetail.averageRating.toInt()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star_selected),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
            repeat(5 - placeDetail.averageRating.toInt()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star_unselected),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Surface(
            modifier = Modifier
                .width(380.dp)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "리뷰작성목록",
                        style = typography.H10_M
                    )

                    Button(
                        onClick = { /* TODO: 리뷰 작성 화면 이동 */ },
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Button1,
                            contentColor = Text5
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(width = 66.dp, height = 26.dp)
                    ) {
                        Text(
                            text = "작성하기",
                            style = typography.H10_M
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                val reviewsToShow = placeDetail.reviews.take(visibleReviewsCount)


                Column {
                    reviewsToShow.forEachIndexed { index, review ->
                        ReviewItem(
                            name = review.userNickname,
                            rating = review.rating.coerceIn(0, 5),
                            text = review.comment
                        )

                        if (index < reviewsToShow.size - 1) {
                            Spacer(modifier = Modifier.height(15.dp))
                            HorizontalDivider(color = Background2, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }

                    if (placeDetail.reviews.size > visibleReviewsCount) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "더보기",
                            style = typography.H10_M,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .clickable { viewModel.loadMoreReviews() }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "장소 추천", style = typography.H4_SB)
        }
        RecommendBox(
            onThumbUpClicked = { viewModel.onThumbUpClicked() },
            onThumbDownClicked = { viewModel.onThumbDownClicked() }
                )

        if (recommendState != null) {
            RecommendModal(
                type = recommendState!!,
                onTimeout = { viewModel.resetRecommend() }
            )
        }


    }
}

@Composable
fun RecommendBox(
    onThumbUpClicked: () -> Unit,
    onThumbDownClicked: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current

    Surface(
        modifier = Modifier
            .width(380.dp)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "이 장소가 적절하다고 생각하시나요?",
                style = typography.H6_M,
                color = Text4
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(100.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_thumbup_unselected),
                    contentDescription = "추천",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onThumbUpClicked() }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_thumbdown_unselected),
                    contentDescription = "비추천",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onThumbDownClicked() }
                )
            }
        }
    }
}



@Composable
fun ReviewItem(name: String, rating: Int, text: String) {
    val typography = LocalbarrierFreeTypographyProvider.current

    Row(verticalAlignment = Alignment.Top) {
        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 10.dp)
        )

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = name, style = typography.H9_M, color = Text3)
                Spacer(modifier = Modifier.width(8.dp))
                repeat(rating) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star_selected),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
                repeat(5 - rating) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star_unselected),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Text(text = text, style = typography.H7_M_5)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PlaceDetailScreenPreview() {
    PlaceDetailScreen(
        onBackClick = {}
    )
}


