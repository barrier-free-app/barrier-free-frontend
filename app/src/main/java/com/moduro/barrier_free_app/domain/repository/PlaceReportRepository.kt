package com.moduro.barrier_free_app.domain.repository

import kotlinx.serialization.json.JsonElement

interface PlaceReportRepository {

    suspend fun postPlaceReport(
        description : String,
        name : String,
        address : String,
        imageType : Int,
        facilities : List<Int>,
        homepage : String,
        openingHours : String,
        contact : String
    ) : Result<JsonElement>
}