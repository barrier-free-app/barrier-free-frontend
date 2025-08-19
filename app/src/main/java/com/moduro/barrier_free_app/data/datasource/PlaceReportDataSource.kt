package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestPlaceReportDto
import kotlinx.serialization.json.JsonElement

interface PlaceReportDataSource {

    suspend fun postPlaceReport(requestPlaceReportDto: RequestPlaceReportDto) : ModuroBaseResponse<JsonElement>

}