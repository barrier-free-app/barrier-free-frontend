package com.moduro.barrier_free_app.domain.repository

import com.moduro.barrier_free_app.data.dto.response.LocationNamesResponseDto

interface LocationNameRepository {
    suspend fun getAddress(latitude: Double, longitude: Double): Result<LocationNamesResponseDto>
}