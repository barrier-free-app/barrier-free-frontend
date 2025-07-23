package com.moduro.barrier_free_app.domain.entity

import com.moduro.barrier_free_app.data.dto.response.LocationTempItemDto


data class TemperatureEntity(
    val temperature: String?, // T1H
    val sky: String?,         // SKY
    val rain: String?,        // RN1
    val time: String,         // 공통 시간 (T1H의 시간 기준)
    val date: String          // 공통 날짜 (T1H의 날짜 기준)
)
