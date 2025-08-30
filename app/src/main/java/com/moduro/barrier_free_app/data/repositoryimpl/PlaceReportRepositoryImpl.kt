package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.PlaceReportDataSource
import com.moduro.barrier_free_app.data.dto.request.RequestPlaceReportDto
import com.moduro.barrier_free_app.domain.repository.PlaceReportRepository
import kotlinx.serialization.json.JsonElement
import javax.inject.Inject

class PlaceReportRepositoryImpl @Inject constructor(
    private val placeReportDataSource: PlaceReportDataSource
) : PlaceReportRepository {

    override suspend fun postPlaceReport(
        description: String,
        name: String,
        address: String,
        imageType: Int,
        facilities: List<Int>,
        homepage: String?,
        openingHours: String?,
        contact: String?
    ): Result<JsonElement> {
        return kotlin.runCatching {
            val response = placeReportDataSource.postPlaceReport(
                RequestPlaceReportDto(
                    description = description,
                    name = name,
                    address = address,
                    imageType = imageType,
                    facilities = facilities,
                    homepage = homepage,
                    openingHours = openingHours,
                    contact = contact
                )
            )
            val result = response.result ?: throw Exception("Result is null")

            result
        }
    }

}