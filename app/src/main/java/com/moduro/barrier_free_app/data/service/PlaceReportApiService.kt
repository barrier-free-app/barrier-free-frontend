package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestPlaceReportDto
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.REPORTS
import kotlinx.serialization.json.JsonElement
import retrofit2.http.Body
import retrofit2.http.POST

interface PlaceReportApiService {

    @POST("/$REPORTS")
    suspend fun postPlaceReport(
        @Body requestPlaceReportDto: RequestPlaceReportDto
    ) : ModuroBaseResponse<JsonElement>

}