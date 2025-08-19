package com.moduro.barrier_free_app.data.datasourceimpl

import com.moduro.barrier_free_app.data.datasource.HomeDataSource
import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseHomeHotPlaceDto
import com.moduro.barrier_free_app.data.service.HomeApiService
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val homeApiService: HomeApiService
) : HomeDataSource {
    override suspend fun getHotPlaces(): ModuroBaseResponse<List<ResponseHomeHotPlaceDto>> {
        return homeApiService.getHotPlaces()
    }

    override suspend fun getRecommendPlaces(
        type : String,
        facilities : List<Int>?
    ) : ModuroBaseResponse<List<ResponseHomeHotPlaceDto>> {
        return homeApiService.getRecommendPlaces(type, facilities)
    }
}