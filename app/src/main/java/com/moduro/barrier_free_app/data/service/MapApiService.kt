package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestMapLikeDto
import com.moduro.barrier_free_app.data.dto.response.ResponseHomeHotPlaceDto
import com.moduro.barrier_free_app.data.dto.response.ResponseMapPlaceDto
import com.moduro.barrier_free_app.data.dto.response.ResponseMapPlaceSummDto
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.ALL
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.FAVORITES
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.PLACES
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.POPULARS
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MapApiService {
    @GET("/$PLACES")
    suspend fun getMapPlaces() : ModuroBaseResponse<List<ResponseMapPlaceDto>>

    @GET("/$PLACES/{placeId}/summary")
    suspend fun getMapPlaceSumm(
        @Path("placeId") placeId : Int,
        @Query("placeType") placeType : String
    ) : ModuroBaseResponse<ResponseMapPlaceSummDto>

    @POST("/$PLACES/$FAVORITES")
    suspend fun postMapLike(
        @Body requestMapLikeDto: RequestMapLikeDto
    ) : ModuroBaseResponse<Boolean>

}