package com.moduro.barrier_free_app.data.datasourceimpl

import com.moduro.barrier_free_app.data.datasource.AirKoreaDataSource
import com.moduro.barrier_free_app.data.dto.response.AirItemDto
import com.moduro.barrier_free_app.data.service.AirKoreaApiService
import javax.inject.Inject

class AirKoreaDataSourceImpl @Inject constructor(
    private val apiService: AirKoreaApiService
) : AirKoreaDataSource {
    override suspend fun getAirKorea(serviceKey: String): List<AirItemDto> {
        val response = apiService.getAirKorea(serviceKey = serviceKey)
        return response.response?.body?.items ?: emptyList()
    }
}