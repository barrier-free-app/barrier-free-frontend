package com.moduro.barrier_free_app.data.dto.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationTempBaseResponse(
    @SerialName("response") val response: LocationTempResponseBody
)

@Serializable
data class LocationTempResponseBody(
    @SerialName("body") val body: LocationTempItems
)

@Serializable
data class LocationTempItems(
    @SerialName("items") val items: LocationTempItemList
)

@Serializable
data class LocationTempItemList(
    @SerialName("item") val item: List<LocationTempItemDto>
)

@Serializable
data class LocationTempItemDto(
    @SerialName("category") val category: String,
    @SerialName("fcstValue") val fcstValue: String,
    @SerialName("fcstTime") val fcstTime: String,
    @SerialName("fcstDate") val fcstDate: String
)