package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.FavoriteToggleDataSource
import com.moduro.barrier_free_app.domain.repository.FavoriteToggleRepository
import javax.inject.Inject

class FavoriteToggleRepositoryImpl @Inject constructor(
    private val dataSource: FavoriteToggleDataSource
) : FavoriteToggleRepository {
    override suspend fun toggleFavorite(placeId: Long, type: String): Result<Boolean> {
        return try {
            val response = dataSource.toggleFavorite(placeId)
            if (response.isSuccess) {
                Result.success(response.result?.result ?: false)
            } else {
                Result.failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}