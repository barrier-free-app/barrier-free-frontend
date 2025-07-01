package com.moduro.barrier_free_app.domain.repository

interface AirKoreaRepository {
    suspend fun getPm10Average(serviceKey: String): Result<Double?>
}
