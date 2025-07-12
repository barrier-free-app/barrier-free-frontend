package com.moduro.barrier_free_app.domain.entity

import com.moduro.barrier_free_app.data.dto.response.LocationTempItemDto

data class TemperatureEntity(
    val value: String,
    val time: String,
    val date: String
)

// 3. Extension function
fun LocationTempItemDto.toTemperatureEntity(): TemperatureEntity {
    return TemperatureEntity(
        value = fcstValue,
        time = fcstTime,
        date = fcstDate
    )
}