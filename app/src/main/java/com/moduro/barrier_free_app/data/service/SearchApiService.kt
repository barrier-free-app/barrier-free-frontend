package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponsePlaceSearchResultDto
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.PLACES
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.SEARCH
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("$PLACES/$SEARCH")
    suspend fun getSearchPlaces(
        @Query("keyword") keyword : String?,
        @Query("facilities") facilities : List<Int>?,
        @Query("page") page : Int,
        @Query("size") size : Int = 10,
        @Query("sort") sort : String = "string"
    ) : ModuroBaseResponse<ResponsePlaceSearchResultDto>
}