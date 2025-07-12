package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.response.LocationNamesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationNameApiService {
    @GET("coord2address.json")
    suspend fun getAddressFromCoord(
        @Query("x") longitude: Double,
        @Query("y") latitude: Double
    ): LocationNamesResponseDto
}