package com.moduro.barrier_free_app.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMapPlaceSummDto (
    @SerialName("name") val name : String,
    @SerialName("description") val description : String?,
    @SerialName("address") val address : String?,
    @SerialName("facilities") val facilities : List<Int>,
    @SerialName("placeType") val placeType : String,
    @SerialName("imageType") val imageType : Int,
    @SerialName("favorite") val favorite : Boolean
)