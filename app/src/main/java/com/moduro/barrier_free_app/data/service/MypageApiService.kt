package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseFavoritePlacesResultDto
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.USERS
import retrofit2.http.GET

interface MypageApiService {
    @GET("/$USERS/favorites")
    suspend fun getFavoritePlaces(): ModuroBaseResponse<ResponseFavoritePlacesResultDto>
}