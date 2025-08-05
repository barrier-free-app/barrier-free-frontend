package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseFavoritePlacesResultDto
import com.moduro.barrier_free_app.data.dto.response.ResponseNicknameChange
import com.moduro.barrier_free_app.data.dto.response.ResponseReviewListDto
import com.moduro.barrier_free_app.data.dto.response.ResponseUserDto

interface MypageDataSource {
    suspend fun getUserInfo(): ModuroBaseResponse<ResponseUserDto>
    suspend fun changeNickname(nickname: String): ModuroBaseResponse<ResponseNicknameChange>
    suspend fun updateUserType(userType: String): ModuroBaseResponse<String>
    suspend fun getFavoritePlaces(): ModuroBaseResponse<ResponseFavoritePlacesResultDto>
    suspend fun getReviewPlaces(): ModuroBaseResponse<ResponseReviewListDto>
}
