package com.moduro.barrier_free_app.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseHomeHotPlaceDto (
    @SerialName("placeId") val placeId : Long,
    @SerialName("placeType") val placeType : String,
    @SerialName("name") val name : String,
    @SerialName("region") val region : String,
    @SerialName("description") val description : String,
    @SerialName("facility") val facility : List<Int>,
    @SerialName("imageType") val imageType : Int,

)