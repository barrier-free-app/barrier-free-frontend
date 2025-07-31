package com.moduro.barrier_free_app.domain.repository

import com.moduro.barrier_free_app.data.dto.response.ResponsePlaceSearchResultDto
import com.moduro.barrier_free_app.domain.entity.SearchPlaceResultEntity

interface SearchRepository {
    suspend fun getSearchPlaces(
        keyword : String?,
        facilities : List<Int>?,
        page : Int,
        size : Int = 10
    ) : Result<SearchPlaceResultEntity>
}