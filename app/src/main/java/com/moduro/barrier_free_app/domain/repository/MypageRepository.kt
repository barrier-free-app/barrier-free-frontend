package com.moduro.barrier_free_app.domain.repository

import com.moduro.barrier_free_app.domain.entity.FavoritePlaceEntity
import com.moduro.barrier_free_app.domain.entity.ReviewPlaceEntity

interface MypageRepository{
    suspend fun getFavoritePlaces() : Result<List<FavoritePlaceEntity>>
    suspend fun getReviewPlaces() : Result<List<ReviewPlaceEntity>>
}