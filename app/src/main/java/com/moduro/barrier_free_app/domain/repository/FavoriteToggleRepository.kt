package com.moduro.barrier_free_app.domain.repository

interface FavoriteToggleRepository {
    suspend fun toggleFavorite(placeId: Long, type: String = "map"): Result<Boolean>
}