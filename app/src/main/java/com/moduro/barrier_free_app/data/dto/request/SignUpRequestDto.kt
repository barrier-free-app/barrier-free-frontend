package com.moduro.barrier_free_app.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto (
    @SerialName("email") val email: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("username") val username: String,
    @SerialName("password") val password: String,
    @SerialName("verifyPassword") val verifyPassword: String,
    @SerialName("userType") val userType: String,
    @SerialName("userFacilityIds") val userFacilityIds: List<Int>
)
