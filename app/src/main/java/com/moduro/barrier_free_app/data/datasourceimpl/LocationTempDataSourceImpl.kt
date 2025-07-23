package com.moduro.barrier_free_app.data.datasourceimpl

import com.moduro.barrier_free_app.data.datasource.LocationTempDataSource
import com.moduro.barrier_free_app.data.dto.response.LocationTempBaseResponse
import com.moduro.barrier_free_app.data.service.LocationTempApiService
import javax.inject.Inject

class LocationTempDataSourceImpl @Inject constructor(
    private val locationTempApiService: LocationTempApiService
) : LocationTempDataSource {

    override suspend fun getLocationTempData(
        serviceKey: String,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): LocationTempBaseResponse {
        return locationTempApiService.getLocationTempData(
            serviceKey = serviceKey,
            baseDate = baseDate,
            baseTime = baseTime,
            nx = nx,
            ny = ny
        )
    }
}