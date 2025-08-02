package com.moduro.barrier_free_app.domain.repository

import com.moduro.barrier_free_app.domain.entity.FavoritePlaceEntity

interface MypageRepository{
    suspend fun getFavoritePlaces() : Result<List<FavoritePlaceEntity>>
}