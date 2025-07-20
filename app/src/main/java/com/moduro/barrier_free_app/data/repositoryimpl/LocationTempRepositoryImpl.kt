package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.LocationTempDataSource
import com.moduro.barrier_free_app.domain.entity.TemperatureEntity
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
            val items = response.response.body.items.item

            // 가장 빠른 시간의 T1H를 기준으로 시간과 날짜를 가져옴
            val t1hItem = items
                .filter { it.category == "T1H" }
                .minByOrNull { "${it.fcstDate}${it.fcstTime.padStart(4, '0')}" }

            val skyItem = items
                .filter { it.category == "SKY" }
                .minByOrNull { "${it.fcstDate}${it.fcstTime.padStart(4, '0')}" }

            val rn1Item = items
                .filter { it.category == "RN1" }
                .minByOrNull { "${it.fcstDate}${it.fcstTime.padStart(4, '0')}" }

            t1hItem?.let {
                TemperatureEntity(
                    temperature = t1hItem.fcstValue,
                    sky = skyItem?.fcstValue,
                    rain = rn1Item?.fcstValue,
                    time = t1hItem.fcstTime,
                    date = t1hItem.fcstDate
                )
            }
        }
    }

}