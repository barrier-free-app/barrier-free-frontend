package com.moduro.barrier_free_app.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePlaceSearchDto (
    @SerialName("placeId") val placeId : Long,
    @SerialName("placeType") val placeType : String,
    @SerialName("facilities") val facilities : List<Int>,
    @SerialName("region") val region : String,
    @SerialName("name") val name : String,
    @SerialName("imageType") val imageType : Int
)

@Serializable
data class ResponsePlaceSearchResultDto(
    @SerialName("placeSearchResponses") val placeSearchResponses : List<ResponsePlaceSearchDto>,
    @SerialName("page") val page : Int,
    @SerialName("size") val size : Int,
    @SerialName("totalPages") val totalPages : Int,
    @SerialName("totalElements") val totalElements : Int,
    @SerialName("hasNext") val hasNext : Boolean
)