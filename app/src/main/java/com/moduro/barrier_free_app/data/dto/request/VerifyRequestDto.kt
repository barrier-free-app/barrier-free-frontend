package com.moduro.barrier_free_app.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyRequestDto (
    @SerialName("email") val email: String,
    @SerialName("verificationCode") val verificationCode: String
)