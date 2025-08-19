package com.moduro.barrier_free_app.data.datasourceimpl

import androidx.compose.runtime.key
import com.moduro.barrier_free_app.data.datasource.MapDataSource
import com.moduro.barrier_free_app.data.datasource.SearchDataSource
import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestMapLikeDto
import com.moduro.barrier_free_app.data.dto.response.ResponseMapPlaceDto
import com.moduro.barrier_free_app.data.dto.response.ResponseMapPlaceSummDto
import com.moduro.barrier_free_app.data.dto.response.ResponsePlaceSearchResultDto
import com.moduro.barrier_free_app.data.service.MapApiService
import com.moduro.barrier_free_app.data.service.SearchApiService
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchDataSource {

    override suspend fun getSearchPlaces(
        keyword: String?,
        facilities: List<Int>?,
        page: Int,
        size: Int
    ): ModuroBaseResponse<ResponsePlaceSearchResultDto> {
        return searchApiService.getSearchPlaces(keyword, facilities, page, size)
    }

}