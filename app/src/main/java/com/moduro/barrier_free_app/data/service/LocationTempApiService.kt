package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.response.LocationTempBaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationTempApiService {
    @GET("getUltraSrtFcst")
    suspend fun getLocationTempData(
        @Query("serviceKey", encoded = true) serviceKey: String,
        @Query("numOfRows") numOfRows: Int = 60,
        @Query("pageNo") pageNo: Int = 1,
        @Query("dataType") dataType: String = "JSON",
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int
    ): LocationTempBaseResponse
}