package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestFavoriteToggle
import com.moduro.barrier_free_app.data.dto.response.ResponseFavoriteToggle
import retrofit2.http.Body
import retrofit2.http.POST

interface FavoriteToggleApiService {
    @POST("/favorites")
    suspend fun toggleFavorite(
        @Body request: RequestFavoriteToggle
    ): ModuroBaseResponse<ResponseFavoriteToggle>
}