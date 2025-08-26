package com.moduro.barrier_free_app.domain.entity

data class FavoritePlaceEntity(
    val id: Long,
    val type: String,
    val name: String,
    val description: String,
    val facilities: List<Int>,
    val imageType: Int,
    val favorite: Boolean
)
