package com.moduro.barrier_free_app.domain.repository

import com.moduro.barrier_free_app.domain.entity.TemperatureEntity

interface LocationTempRepository {
    suspend fun getLatestTemperature(
        serviceKey: String,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): Result<TemperatureEntity?>
}