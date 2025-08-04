package com.moduro.barrier_free_app.data.datasourceimpl

import com.moduro.barrier_free_app.data.datasource.MypageDataSource
import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestNicknameChange
import com.moduro.barrier_free_app.data.dto.request.RequestUpdateFacilityDto
import com.moduro.barrier_free_app.data.dto.request.RequestUpdatePasswordDto
import com.moduro.barrier_free_app.data.dto.request.RequestUserTypeUpdate
import com.moduro.barrier_free_app.data.dto.response.ResponseFavoritePlacesResultDto
import com.moduro.barrier_free_app.data.dto.response.ResponseNicknameChange
import com.moduro.barrier_free_app.data.dto.response.ResponseReviewListDto
import com.moduro.barrier_free_app.data.dto.response.ResponseUpadatePasswordDto
import com.moduro.barrier_free_app.data.dto.response.ResponseUpdateFacilityDto
import com.moduro.barrier_free_app.data.dto.response.ResponseUserDto
import com.moduro.barrier_free_app.data.service.MypageApiService
import javax.inject.Inject

class MypageDataSourceImpl @Inject constructor(
    private val mypageApiService: MypageApiService) : MypageDataSource {

    override suspend fun getUserInfo(): ModuroBaseResponse<ResponseUserDto> {
        return mypageApiService.getUserInfo()
    }

    override suspend fun changeNickname(nickname: String): ModuroBaseResponse<ResponseNicknameChange> {
        val request = RequestNicknameChange(nickname = nickname)
        return mypageApiService.changeNickname(request)
    }

    override suspend fun updatePassword(
        password: String,
        verifyPassword: String
    ): ModuroBaseResponse<ResponseUpadatePasswordDto> {
        val request = RequestUpdatePasswordDto(password = password, verifyPassword = verifyPassword)
        return mypageApiService.updatePassword(request)
    }

    override suspend fun updateUserType(userType: String): ModuroBaseResponse<String> {
        val request = RequestUserTypeUpdate(userType = userType)
        return mypageApiService.updateUserType(request)
    }

    override suspend fun updateFacilities(facilityIds: List<Int>): ModuroBaseResponse<ResponseUpdateFacilityDto> {
        val request = RequestUpdateFacilityDto(facilityIds = facilityIds)
        return mypageApiService.updateFacilities(request)
    }

    override suspend fun getFavoritePlaces(): ModuroBaseResponse<ResponseFavoritePlacesResultDto> {
        return mypageApiService.getFavoritePlaces()
    }

    override suspend fun getReviewPlaces(): ModuroBaseResponse<ResponseReviewListDto> {
        return mypageApiService.getReviewPlaces()
    }


}