package com.moduro.barrier_free_app.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestMapLikeDto (
    @SerialName("placeId") val placeId: Long,
    @SerialName("type") val type : String
)