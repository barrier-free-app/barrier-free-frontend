package com.moduro.barrier_free_app.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserDto(
    @SerialName("userId") val userId: Long,
    @SerialName("email") val email: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("userType") val userType: String,
    @SerialName("userFacilities") val userFacilities: List<FacilityResponse>,
    @SerialName("socialType") val socialType: String
)

@Serializable
data class FacilityResponse(
    @SerialName("facilityId") val facilityId: Int,
    @SerialName("facilityName") val facilityName: String,
)