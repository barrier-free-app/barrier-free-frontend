package com.moduro.barrier_free_app.data.datasourceimpl

import android.util.Log
import com.moduro.barrier_free_app.data.datasource.LocationNameDataSource
import com.moduro.barrier_free_app.data.dto.response.LocationNamesResponseDto
import com.moduro.barrier_free_app.data.service.LocationNameApiService
import javax.inject.Inject

class LocationNameDataSourceImpl @Inject constructor(
    private val apiService: LocationNameApiService
) : LocationNameDataSource {
    override suspend fun getAddress(latitude: Double, longitude: Double): LocationNamesResponseDto {
        Log.d("HomeScreen", "getLocationName datasource 호출 완료")
        return apiService.getAddressFromCoord(longitude, latitude)
    }
}