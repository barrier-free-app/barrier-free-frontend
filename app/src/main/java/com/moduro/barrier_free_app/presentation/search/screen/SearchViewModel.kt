package com.moduro.barrier_free_app.presentation.search.screen

import androidx.lifecycle.ViewModel
import com.moduro.barrier_free_app.domain.entity.HomePlaceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    val dummySearchPlaces = listOf(
        HomePlaceEntity(
            id = 2,
            type = 1,
            name = "서울역사박물관",
            location = "서울 종로구",
            description = "배리어프리 서비스 도입 미술관",
            facilities = listOf("승강기", "장애인 화장실"),
            isReported = true
        ),
        HomePlaceEntity(
            id = 3,
            type = 2,
            name = "파크하얏트 서울",
            location = "서울 강남구",
            description = "배리어프리룸 보유 호텔",
            facilities = listOf("수유실", "영유아 동반"),
            isReported = false
        ),
        HomePlaceEntity(
            id = 4,
            type = 3,
            name = "쇼어",
            location = "서울 종로구",
            description = "아이와 함께 가기 좋은 실내 카페",
            facilities = listOf("장애인 화장실", "승강기", "수유실", "경사로"),
            isReported = true
        ),
        HomePlaceEntity(
            id = 5,
            type = 4,
            name = "서울역사박물관",
            location = "서울 종로구",
            description = "배리어프리 서비스 도입 미술관",
            facilities = listOf("승강기", "장애인 화장실"),
            isReported = true
        ),
        HomePlaceEntity(
            id = 6,
            type = 5,
            name = "파크하얏트 서울",
            location = "서울 강남구",
            description = "배리어프리룸 보유 호텔",
            facilities = listOf("수유실", "영유아 동반"),
            isReported = false
        ),
        HomePlaceEntity(
            id = 7,
            type = 6,
            name = "쇼어",
            location = "서울 종로구",
            description = "아이와 함께 가기 좋은 실내 카페",
            facilities = listOf("장애인 화장실", "승강기", "수유실", "경사로"),
            isReported = false
        ),
        HomePlaceEntity(
            id = 8,
            type = 1,
            name = "서울역사박물관",
            location = "서울 종로구",
            description = "배리어프리 서비스 도입 미술관",
            facilities = listOf("승강기", "장애인 화장실"),
            isReported = true
        ),
        HomePlaceEntity(
            id = 9,
            type = 3,
            name = "파크하얏트 서울",
            location = "서울 강남구",
            description = "배리어프리룸 보유 호텔",
            facilities = listOf("수유실", "영유아 동반"),
            isReported = false
        ),
        HomePlaceEntity(
            id = 10,
            type = 4,
            name = "쇼어",
            location = "서울 종로구",
            description = "아이와 함께 가기 좋은 실내 카페",
            facilities = listOf("장애인 화장실", "승강기", "수유실", "경사로"),
            isReported = false
        )

    )



}