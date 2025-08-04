package com.moduro.barrier_free_app.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDeleteAccountDto(
    @SerialName("result") val result: String
)