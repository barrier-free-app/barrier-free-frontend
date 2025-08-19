package com.moduro.barrier_free_app.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPlaceReportDto (
    @SerialName("description") val description : String,
    @SerialName("name") val name : String,
    @SerialName("address") val address : String,
    @SerialName("imageType") val imageType : Int,
    @SerialName("facilities") val facilities : List<Int>,
    @SerialName("homepage") val homepage : String,
    @SerialName("openingHours") val openingHours : String,
    @SerialName("contact") val contact : String,
    )