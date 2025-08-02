package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.MypageDataSource
import com.moduro.barrier_free_app.domain.entity.FavoritePlaceEntity
import com.moduro.barrier_free_app.domain.repository.MypageRepository
import javax.inject.Inject

class MypageRepositoryImpl @Inject constructor(
    private val mypageDataSource: MypageDataSource
) : MypageRepository {
    override suspend fun getFavoritePlaces(): Result<List<FavoritePlaceEntity>> {
        return runCatching {
            val response = mypageDataSource.getFavoritePlaces()
            val result = response.result ?: throw Exception("Result is null")

            (result.mapFavorites + result.reportFavorites).map { dto ->
                FavoritePlaceEntity(
                    id = dto.placeId,
                    type = dto.placeType,
                    name = dto.name,
                    description = dto.description,
                    facilities = dto.facility,
                    imageType = dto.imageType,
                    favorite = dto.favorite
                )
            }
        }
    }
}
