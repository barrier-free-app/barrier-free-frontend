package com.moduro.barrier_free_app.domain.repository

import com.moduro.barrier_free_app.domain.entity.FavoritePlaceEntity
import com.moduro.barrier_free_app.domain.entity.ReviewPlaceEntity
import com.moduro.barrier_free_app.domain.entity.UserEntity

interface MypageRepository{
    suspend fun getUserInfo() : Result<UserEntity>
    suspend fun changeNickname(newNickname: String): Result<String>
    suspend fun updatePassword(password: String, verifyPassword: String): Result<String>
    suspend fun updateUserType(userType: String) : Result<String>
    suspend fun updateFacilities(facilityIds: List<Int>) : Result<String>
    suspend fun getFavoritePlaces() : Result<List<FavoritePlaceEntity>>
    suspend fun getReviewPlaces() : Result<List<ReviewPlaceEntity>>
}