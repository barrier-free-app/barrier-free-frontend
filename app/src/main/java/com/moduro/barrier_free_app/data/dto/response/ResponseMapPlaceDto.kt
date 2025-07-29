package com.moduro.barrier_free_app.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMapPlaceDto (
    @SerialName("id") val id : Long,
    @SerialName("latitude") val latitude : Double,
    @SerialName("longitude") val longitude : Double,
    @SerialName("name") val name : String,
    @SerialName("region") val region : String,
    @SerialName("placeType") val placeType : String,
    )