package com.moduro.barrier_free_app.data.datasourceimpl

import com.moduro.barrier_free_app.data.datasource.PlaceReportDataSource
import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestPlaceReportDto
import com.moduro.barrier_free_app.data.service.PlaceReportApiService
import kotlinx.serialization.json.JsonElement
import javax.inject.Inject

class PlaceReportDataSourceImpl @Inject constructor(
    private val placeReportApiService: PlaceReportApiService
) : PlaceReportDataSource {

    override suspend fun postPlaceReport(requestPlaceReportDto: RequestPlaceReportDto): ModuroBaseResponse<JsonElement> {
        return placeReportApiService.postPlaceReport(requestPlaceReportDto)
    }
}