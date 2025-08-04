package com.moduro.barrier_free_app.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestUserTypeUpdate(
    @SerialName("userType")
    val userType: String
)