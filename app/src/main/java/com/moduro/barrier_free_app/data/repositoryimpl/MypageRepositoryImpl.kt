package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.MypageDataSource
import com.moduro.barrier_free_app.domain.entity.FacilityEntity
import com.moduro.barrier_free_app.domain.entity.FavoritePlaceEntity
import com.moduro.barrier_free_app.domain.entity.ReviewPlaceEntity
import com.moduro.barrier_free_app.domain.entity.UserEntity
import com.moduro.barrier_free_app.domain.repository.MypageRepository
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
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
            val responseReviewListDto =
                response.result ?: throw Exception("ResponseReviewListDto is null")
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

    override suspend fun changeNickname(nickname: String): Result<String> {
        return try {
            val response = mypageDataSource.changeNickname(nickname)
            if (response.isSuccess && response.result != null) {
                Result.success(response.result.result)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: retrofit2.HttpException) {
            // 에러 응답 바디에서 message 추출
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = parseErrorMessage(errorBody)
            Result.failure(Exception(errorMessage ?: "닉네임 변경 실패 (HTTP ${e.code()})"))
        } catch (e: Exception) {
            Result.failure(Exception("닉네임 변경 실패: ${e.message}"))
        }
    }

    private fun parseErrorMessage(json: String?): String? {
        return try {
            val jsonObject = kotlinx.serialization.json.Json.parseToJsonElement(json ?: return null).jsonObject
            jsonObject["message"]?.jsonPrimitive?.content
        } catch (e: Exception) {
            null
        }
    }



    override suspend fun getUserInfo(): Result<UserEntity> {
        return runCatching {
            val response = mypageDataSource.getUserInfo()
            val result = response.result ?: throw Exception("User result is null")

            UserEntity(
                userId = result.userId,
                email = result.email,
                nickname = result.nickname,
                userType = result.userType,
                socialType = result.socialType,
                userFacilities = result.userFacilities.map { facilityDto ->
                    FacilityEntity(
                        facilityId = facilityDto.facilityId,
                        facilityName = facilityDto.facilityName
                    )
                }
            )
        }
    }

}
