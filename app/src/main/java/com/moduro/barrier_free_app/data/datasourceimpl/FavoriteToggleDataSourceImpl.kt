package com.moduro.barrier_free_app.data.datasourceimpl

import com.moduro.barrier_free_app.data.datasource.FavoriteToggleDataSource
import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.RequestFavoriteToggle
import com.moduro.barrier_free_app.data.dto.response.ResponseFavoriteToggle
import com.moduro.barrier_free_app.data.service.FavoriteToggleApiService
import javax.inject.Inject

class FavoriteToggleDataSourceImpl @Inject constructor(
    private val favoriteToggleApiService: FavoriteToggleApiService
) : FavoriteToggleDataSource {

    override suspend fun toggleFavorite(placeId: Long, type: String): ModuroBaseResponse<ResponseFavoriteToggle> {
        val request = RequestFavoriteToggle(placeId = placeId, type = type)
        return favoriteToggleApiService.toggleFavorite(request)
    }
}
