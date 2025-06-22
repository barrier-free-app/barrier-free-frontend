package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.response.AirItemDto

interface AirKoreaDataSource {
    suspend fun getAirKorea(serviceKey : String) : List<AirItemDto>

}