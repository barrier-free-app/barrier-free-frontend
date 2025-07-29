package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestMapLikeDto
import com.moduro.barrier_free_app.data.dto.response.ResponseHomeHotPlaceDto
import com.moduro.barrier_free_app.data.dto.response.ResponseMapPlaceDto
import com.moduro.barrier_free_app.data.dto.response.ResponseMapPlaceSummDto

interface MapDataSource {
    suspend fun getMapPlaces() : ModuroBaseResponse<List<ResponseMapPlaceDto>>

    suspend fun getMapPlaceSumm(placeId : Int, placeType : String) : ModuroBaseResponse<ResponseMapPlaceSummDto>

    suspend fun postMapLike(requestMapLikeDto: RequestMapLikeDto) : ModuroBaseResponse<Boolean>

}