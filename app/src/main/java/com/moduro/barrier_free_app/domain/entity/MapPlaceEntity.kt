package com.moduro.barrier_free_app.domain.entity

data class MapPlaceEntity (
    val id: Long,
    val gu : String,
    val facilities: List<String>,
    val latitude: Double,
    val longitude: Double
)