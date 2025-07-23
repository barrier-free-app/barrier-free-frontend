package com.moduro.barrier_free_app.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LocationNamesResponseDto(
    val documents: List<Document>
)

@Serializable
data class Document(
    @SerialName("region_2depth_name")
    val region2depthName: String,
    @SerialName("region_3depth_name")
    val region3depthName: String
)
