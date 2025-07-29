package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.MapDataSource
import com.moduro.barrier_free_app.data.dto.request.RequestMapLikeDto
import com.moduro.barrier_free_app.domain.entity.MapPlaceEntity
import com.moduro.barrier_free_app.domain.entity.MapPlaceSummEntity
import com.moduro.barrier_free_app.domain.repository.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapDataSource: MapDataSource
) : MapRepository {
    override suspend fun getMapPlaces(): Result<List<MapPlaceEntity>> {
        return runCatching {
            val response = mapDataSource.getMapPlaces()

            val result = response.result ?: throw Exception("Result is null")

            result.map { place ->
                MapPlaceEntity(
                    id = place.id,
                    name = place.name,
                    latitude = place.latitude,
                    longitude = place.longitude,
                    region = place.region,
                    placeType = place.placeType,
                )

            }
        }

    }

    override suspend fun getMapPlaceSumm(
        placeId: Int,
        placeType: String
    ): Result<MapPlaceSummEntity> {
        return runCatching {
            val response = mapDataSource.getMapPlaceSumm(placeId, placeType)
            val result = response.result ?: throw Exception("Result is null")

            MapPlaceSummEntity(
                name = result.name,
                description = result.description,
                address = result.address,
                facilities = result.facilities,
                placeType = result.placeType,
                imageType = result.imageType,
                favorite = result.favorite
            )
        }
    }

    override suspend fun postMapLike(placeId: Long, type: String): Result<Boolean> {
        return kotlin.runCatching {
            val response = mapDataSource.postMapLike(
                RequestMapLikeDto(
                    placeId = placeId,
                    type = type
                )
            )
            val result = response.result ?: throw Exception("Result is null")

            result
        }
    }

}