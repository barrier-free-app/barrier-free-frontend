package com.moduro.barrier_free_app.domain.repository

import com.moduro.barrier_free_app.data.dto.response.ResponseHomeHotPlaceDto
import com.moduro.barrier_free_app.domain.entity.HomePlaceEntity

interface HomeRepository{
    suspend fun getHotPlaces() : Result<List<HomePlaceEntity>>

    suspend fun getRecommendPlaces(
        type : String?,
        facilities : List<Int>?
    ) : Result<List<HomePlaceEntity>>
}