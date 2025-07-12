package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.LocationTempDataSource
import com.moduro.barrier_free_app.domain.entity.TemperatureEntity
import com.moduro.barrier_free_app.domain.entity.toTemperatureEntity
import com.moduro.barrier_free_app.domain.repository.LocationTempRepository
import javax.inject.Inject

class LocationTempRepositoryImpl @Inject constructor(
    private val locationTempDataSource: LocationTempDataSource
) : LocationTempRepository {

    override suspend fun getLatestTemperature(
        serviceKey: String,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): Result<TemperatureEntity?> {
        return runCatching {
            val response = locationTempDataSource.getLocationTempData(serviceKey, baseDate, baseTime, nx, ny)

            response.response.body.items.item
                .filter { it.category == "T1H" }
                .minByOrNull { "${it.fcstDate}${it.fcstTime.padStart(4, '0')}" }
                ?.toTemperatureEntity()
        }
    }
}