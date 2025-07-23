package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.response.LocationTempBaseResponse

interface LocationTempDataSource {
    suspend fun getLocationTempData(
        serviceKey: String,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): LocationTempBaseResponse
}