package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.HomeDataSource
import com.moduro.barrier_free_app.data.dto.response.ResponseHomeHotPlaceDto
import com.moduro.barrier_free_app.domain.entity.HomePlaceEntity
import com.moduro.barrier_free_app.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource
) : HomeRepository {
    override suspend fun getHotPlaces(): Result<List<HomePlaceEntity>> {
        return runCatching {
            val response = homeDataSource.getHotPlaces()

            val result = response.result ?: throw Exception("Result is null")

            result.map { place ->
               HomePlaceEntity(
                    placeId = place.placeId,
                    placeType = place.placeType,
                    name = place.name,
                    region = place.region,
                    description = place.description,
                    facility = place.facility,
                    imageType = place.imageType
                )

            }
        }
    }

    override suspend fun getRecommendPlaces(
        type : String?,
        facilities : List<Int>?,
    ): Result<List<HomePlaceEntity>> {
        return runCatching {
            val response = homeDataSource.getRecommendPlaces(type, facilities)

            val result = response.result ?: throw Exception("Result is null")

            result.map { place ->
                HomePlaceEntity(
                    placeId = place.placeId,
                    placeType = place.placeType,
                    name = place.name,
                    region = place.region,
                    description = place.description,
                    facility = place.facility,
                    imageType = place.imageType
                )

            }
        }
    }
}