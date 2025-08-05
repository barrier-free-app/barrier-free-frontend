package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseHomeHotPlaceDto

interface HomeDataSource {
    suspend fun getHotPlaces() : ModuroBaseResponse<List<ResponseHomeHotPlaceDto>>

    suspend fun getRecommendPlaces(
        type : String,
        facilities : List<Int>?
    ) : ModuroBaseResponse<List<ResponseHomeHotPlaceDto>>

}