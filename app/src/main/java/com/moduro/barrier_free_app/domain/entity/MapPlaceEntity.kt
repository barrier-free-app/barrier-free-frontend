package com.moduro.barrier_free_app.domain.entity

data class MapPlaceEntity (
    val id: Long,
    val name : String,
    val type : Int,
    val gu : String,
    val facilities: List<Int>,
    val latitude: Double,
    val longitude: Double
)