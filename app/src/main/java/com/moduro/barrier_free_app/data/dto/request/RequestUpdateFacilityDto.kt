package com.moduro.barrier_free_app.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestUpdateFacilityDto(
    @SerialName("facilityIds") val facilityIds: List<Int>
)