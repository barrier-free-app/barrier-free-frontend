package com.moduro.barrier_free_app.presentation.home.screen

import androidx.lifecycle.ViewModel
import com.moduro.barrier_free_app.domain.entity.HomePlaceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val dummyHotPlace = HomePlaceEntity(
        id = 1,
        type = 1,
        name = "루트205",
        location = "서울 강동구",
        description = "아이와 함께하는 예스키즈존",
        facilities = listOf("수유실", "승강기", "경사로"),
        isReported = true
    )

    val dummyWeatherPlaces = listOf(
        HomePlaceEntity(
            id = 2,
            type = 2,
            name = "서울역사박물관",
            location = "서울 종로구",
            description = "배리어프리 서비스 도입 미술관",
            facilities = listOf("승강기", "장애인 화장실"),
            isReported = true
        ),
        HomePlaceEntity(
            id = 3,
            type = 3,
            name = "파크하얏트 서울",
            location = "서울 강남구",
            description = "배리어프리룸 보유 호텔",
            facilities = listOf("수유실", "영유아 동반"),
            isReported = false
        ),
        HomePlaceEntity(
            id = 4,
            type = 4,
            name = "쇼어",
            location = "서울 종로구",
            description = "아이와 함께 가기 좋은 실내 카페",
            facilities = listOf("장애인 화장실", "승강기", "수유실", "경사로"),
            isReported = true
        )
    )



}
