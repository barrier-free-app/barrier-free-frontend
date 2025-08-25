package com.moduro.barrier_free_app.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenRequestDto(
    @SerialName("type") val type: String,
    @SerialName("authCode") val authCode: String,
    @SerialName("state") val state: String? = null
)