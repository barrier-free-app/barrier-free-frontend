package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.MypageDataSource
import com.moduro.barrier_free_app.domain.entity.FavoritePlaceEntity
import com.moduro.barrier_free_app.domain.entity.ReviewPlaceEntity
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

    override suspend fun getReviewPlaces(): Result<List<ReviewPlaceEntity>> {
        return runCatching {
            val response = mypageDataSource.getReviewPlaces()
            val responseReviewListDto = response.result ?: throw Exception("ResponseReviewListDto is null")
            val reviewListResult = responseReviewListDto.result

            reviewListResult.reviews.map { dto ->
                ReviewPlaceEntity(
                    placeId = dto.placeId,
                    placeName = dto.placeName,
                    placeType = dto.placeType,
                    imageType = dto.imageType,
                    content = dto.content,
                    rating = dto.rating,
                    reviewImageUrls = dto.reviewImageUrls
                )
            }
        }
    }
}
