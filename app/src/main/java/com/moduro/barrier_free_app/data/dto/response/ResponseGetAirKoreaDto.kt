package com.moduro.barrier_free_app.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirItemDto(
    @SerialName("pm10Value") val pm10Value: String? = null,
    @SerialName("pm25Value") val pm25Value: String? = null,
    @SerialName("stationName") val stationName: String? = null,
    @SerialName("dataTime") val dataTime: String? = null
)

@Serializable
data class AirBodyDto(
    @SerialName("items") val items: List<AirItemDto> = emptyList(),
    @SerialName("totalCount") val totalCount: Int = 0
)

@Serializable
data class AirResponseDto(
    @SerialName("response") val response: AirResponseBodyWrapper? = null
)

@Serializable
data class AirResponseBodyWrapper(
    @SerialName("body") val body: AirBodyDto? = null
)
