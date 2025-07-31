package com.moduro.barrier_free_app.data.datasourceimpl

import com.moduro.barrier_free_app.data.datasource.HomeDataSource
import com.moduro.barrier_free_app.data.datasource.MapDataSource
import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestMapLikeDto
import com.moduro.barrier_free_app.data.dto.response.ResponseHomeHotPlaceDto
import com.moduro.barrier_free_app.data.dto.response.ResponseMapPlaceDto
import com.moduro.barrier_free_app.data.dto.response.ResponseMapPlaceSummDto
import com.moduro.barrier_free_app.data.service.HomeApiService
import com.moduro.barrier_free_app.data.service.MapApiService
import javax.inject.Inject

class MapDataSourceImpl @Inject constructor(
    private val mapApiService: MapApiService
) : MapDataSource {
    override suspend fun getMapPlaces(facilities : List<Int>?): ModuroBaseResponse<List<ResponseMapPlaceDto>> {
        return mapApiService.getMapPlaces(facilities)
    }

    override suspend fun getMapPlaceSumm(
        placeId: Int,
        placeType: String
    ): ModuroBaseResponse<ResponseMapPlaceSummDto> {
        return mapApiService.getMapPlaceSumm(placeId, placeType)
    }

    override suspend fun postMapLike(requestMapLikeDto: RequestMapLikeDto): ModuroBaseResponse<Boolean> {
        return mapApiService.postMapLike(requestMapLikeDto)
    }
}