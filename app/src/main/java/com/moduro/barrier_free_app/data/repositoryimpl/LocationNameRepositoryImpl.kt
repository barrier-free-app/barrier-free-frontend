package com.moduro.barrier_free_app.data.repositoryimpl

import android.util.Log
import com.moduro.barrier_free_app.data.datasource.LocationNameDataSource
import com.moduro.barrier_free_app.data.dto.response.LocationNamesResponseDto
import com.moduro.barrier_free_app.domain.repository.LocationNameRepository
import javax.inject.Inject


class LocationNameRepositoryImpl @Inject constructor(
    private val dataSource: LocationNameDataSource
) : LocationNameRepository {
    override suspend fun getAddress(latitude: Double, longitude: Double): Result<LocationNamesResponseDto> {
        Log.d("HomeScreen", "getLocationName repository 호출 완료")
        return runCatching {
            dataSource.getAddress(latitude, longitude)
        }
    }
}