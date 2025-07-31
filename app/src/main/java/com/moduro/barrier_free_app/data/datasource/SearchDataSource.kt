package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestMapLikeDto
import com.moduro.barrier_free_app.data.dto.response.ResponseMapPlaceDto
import com.moduro.barrier_free_app.data.dto.response.ResponseMapPlaceSummDto
import com.moduro.barrier_free_app.data.dto.response.ResponsePlaceSearchResultDto

interface SearchDataSource {

    suspend fun getSearchPlaces(
        keyword : String?,
        facilities: List<Int>?,
        page : Int,
        size : Int = 10
    ) : ModuroBaseResponse<ResponsePlaceSearchResultDto>
}