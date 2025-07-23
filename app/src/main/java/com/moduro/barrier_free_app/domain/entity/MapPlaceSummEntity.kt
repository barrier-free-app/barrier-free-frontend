package com.moduro.barrier_free_app.domain.entity

data class MapPlaceSummEntity (
    val id: Int,
    val name: String,
    val address: String,
    val type : Int,
    val isLike : Boolean
)