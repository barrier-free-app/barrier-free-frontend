package com.moduro.barrier_free_app.data.datasourceimpl

import com.moduro.barrier_free_app.data.datasource.MypageDataSource
import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseFavoritePlacesResultDto
import com.moduro.barrier_free_app.data.service.MypageApiService
import javax.inject.Inject

class MypageDataSourceImpl @Inject constructor(
    private val mypageApiService: MypageApiService) : MypageDataSource {
    override suspend fun getFavoritePlaces(): ModuroBaseResponse<ResponseFavoritePlacesResultDto> {
        return mypageApiService.getFavoritePlaces()
    }

}