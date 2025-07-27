package com.moduro.barrier_free_app.presentation.search.screen

import androidx.lifecycle.ViewModel
import com.moduro.barrier_free_app.domain.entity.HomePlaceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    val dummySearchPlaces = listOf(
        HomePlaceEntity(
            placeId = 1,
            placeType = "map",
            name = "종로 맛집",
            region = "종로구",
            description = "종로구의 유명한 맛집",
            facility = listOf(1, 2, 3),
            imageType = 1

        ),
        HomePlaceEntity(
            placeId = 1,
            placeType = "map",
            name = "종로 맛집",
            region = "종로구",
            description = "종로구의 유명한 맛집",
            facility = listOf(1, 2, 3),
            imageType = 1

        ),
        HomePlaceEntity(
            placeId = 1,
            placeType = "map",
            name = "종로 맛집",
            region = "종로구",
            description = "종로구의 유명한 맛집",
            facility = listOf(1, 2, 3),
            imageType = 1

        ),
        HomePlaceEntity(
            placeId = 1,
            placeType = "map",
            name = "종로 맛집",
            region = "종로구",
            description = "종로구의 유명한 맛집",
            facility = listOf(1, 2, 3),
            imageType = 1

        ),
        HomePlaceEntity(
            placeId = 1,
            placeType = "map",
            name = "종로 맛집",
            region = "종로구",
            description = "종로구의 유명한 맛집",
            facility = listOf(1, 2, 3),
            imageType = 1

        ),
        HomePlaceEntity(
            placeId = 1,
            placeType = "map",
            name = "종로 맛집",
            region = "종로구",
            description = "종로구의 유명한 맛집",
            facility = listOf(1, 2, 3),
            imageType = 1

        ),

    )



}