package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseFavoriteToggle

interface FavoriteToggleDataSource {
    suspend fun toggleFavorite(placeId: Long, type: String = "map"): ModuroBaseResponse<ResponseFavoriteToggle>
}