package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseFavoritePlacesResultDto

interface MypageDataSource {
    suspend fun getFavoritePlaces(): ModuroBaseResponse<ResponseFavoritePlacesResultDto>
}
