package com.moduro.barrier_free_app.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestUpdatePasswordDto(
    @SerialName("password") val password: String,
    @SerialName("verifyPassword") val verifyPassword: String
)