package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.response.AirResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AirKoreaApiService {
    @GET("getCtprvnRltmMesureDnsty")
    suspend fun getAirKorea(
        @Query("sidoName") sidoName: String = "서울",
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 100,
        @Query("returnType") returnType: String = "json",
        @Query("serviceKey", encoded = true) serviceKey: String,
        @Query("ver") ver: String = "1.0"
    ): AirResponseDto
}
