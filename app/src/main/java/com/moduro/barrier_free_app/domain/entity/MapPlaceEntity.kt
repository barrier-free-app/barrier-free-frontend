package com.moduro.barrier_free_app.domain.entity

data class MapPlaceEntity (
    val id: Long,
    val name : String,
    val latitude: Double,
    val longitude: Double,
    val region : String,
    val placeType : String,

)