package com.moduro.barrier_free_app.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FindRequestDto (
    @SerialName("email") val email: String
)