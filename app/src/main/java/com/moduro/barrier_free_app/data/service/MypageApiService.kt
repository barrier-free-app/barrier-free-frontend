package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestDeleteAccountDto
import com.moduro.barrier_free_app.data.dto.request.RequestNicknameChange
import com.moduro.barrier_free_app.data.dto.request.RequestUpdateFacilityDto
import com.moduro.barrier_free_app.data.dto.request.RequestUpdatePasswordDto
import com.moduro.barrier_free_app.data.dto.request.RequestUserTypeUpdate
import com.moduro.barrier_free_app.data.dto.response.ResponseDeleteAccountDto
import com.moduro.barrier_free_app.data.dto.response.ResponseFavoritePlacesResultDto
import com.moduro.barrier_free_app.data.dto.response.ResponseNicknameChange
import com.moduro.barrier_free_app.data.dto.response.ResponseReviewListDto
import com.moduro.barrier_free_app.data.dto.response.ResponseUpadatePasswordDto
import com.moduro.barrier_free_app.data.dto.response.ResponseUpdateFacilityDto
import com.moduro.barrier_free_app.data.dto.response.ResponseUserDto
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.ME
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.USERS
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Query

interface MypageApiService {

    @GET("/$USERS/$ME")
    suspend fun getUserInfo(): ModuroBaseResponse<ResponseUserDto>

    @PUT("/$USERS/my-nickname")
    suspend fun changeNickname(
        @Body request: RequestNicknameChange
    ): ModuroBaseResponse<ResponseNicknameChange>

    @PUT("/$USERS/my-type")
    suspend fun updateUserType(
        @Body request: RequestUserTypeUpdate
    ): ModuroBaseResponse<String>

    @PUT("/$USERS/my-facilities")
    suspend fun updateFacilities(
        @Body request: RequestUpdateFacilityDto
    ): ModuroBaseResponse<ResponseUpdateFacilityDto>

    @PATCH("/$USERS/my-password")
    suspend fun updatePassword(
        @Body request: RequestUpdatePasswordDto
    ): ModuroBaseResponse<ResponseUpadatePasswordDto>

    @GET("/$USERS/favorites")
    suspend fun getFavoritePlaces(): ModuroBaseResponse<ResponseFavoritePlacesResultDto>

    @GET("/$USERS/reviews")
    suspend fun getReviewPlaces(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sort") sort: String = "createdAt,DESC"
    ): ModuroBaseResponse<ResponseReviewListDto>

    @DELETE("/$USERS/delete")
    suspend fun deleteAccount(
        @Body request: RequestDeleteAccountDto
    ): ModuroBaseResponse<ResponseDeleteAccountDto>
}