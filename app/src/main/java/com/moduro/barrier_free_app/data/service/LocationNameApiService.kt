package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.response.LocationNamesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationNameApiService {
    @GET("coord2regioncode.json")
    suspend fun getAddressFromCoord(
        @Query("input_coord") input_coord: String = "WGS84",
        @Query("output_coord") output_coord: String = "WGS84",
        @Query("x") longitude: Double,
        @Query("y") latitude: Double
    ): LocationNamesResponseDto
}