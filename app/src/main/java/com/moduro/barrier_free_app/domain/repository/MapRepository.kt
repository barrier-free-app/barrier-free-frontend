package com.moduro.barrier_free_app.domain.repository

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.domain.entity.MapPlaceEntity
import com.moduro.barrier_free_app.domain.entity.MapPlaceSummEntity

interface MapRepository {
    suspend fun getMapPlaces() : Result<List<MapPlaceEntity>>

    suspend fun getMapPlaceSumm(placeId : Int, placeType : String) : Result<MapPlaceSummEntity>

    suspend fun postMapLike(placeId : Long, type : String) : Result<Boolean>
}