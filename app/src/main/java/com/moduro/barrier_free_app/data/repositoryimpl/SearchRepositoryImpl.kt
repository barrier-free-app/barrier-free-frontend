package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.SearchDataSource
import com.moduro.barrier_free_app.domain.entity.SearchPlaceEntity
import com.moduro.barrier_free_app.domain.entity.SearchPlaceResultEntity
import com.moduro.barrier_free_app.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource
) : SearchRepository {

    override suspend fun getSearchPlaces(
        keyword: String?,
        facilities: List<Int>?,
        page: Int,
        size: Int
    ): Result<SearchPlaceResultEntity> {
        return runCatching {
            val response = searchDataSource.getSearchPlaces(keyword, facilities, page, size)
            val result = response.result ?: throw Exception("Result is null")

            SearchPlaceResultEntity(
                placeSearchResponses = result.placeSearchResponses.map { dto ->
                    SearchPlaceEntity(
                        placeId = dto.placeId,
                        placeType = dto.placeType,
                        facilities = dto.facilities,
                        region = dto.region,
                        name = dto.name,
                        imageType = dto.imageType
                    )
                },
                page = result.page,
                size = result.size,
                totalPages = result.totalPages,
                totalElements = result.totalElements,
                hasNext = result.hasNext
            )
        }
    }

}