package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.response.LocationNamesResponseDto

interface LocationNameDataSource {
    suspend fun getAddress(latitude: Double, longitude: Double): LocationNamesResponseDto
}