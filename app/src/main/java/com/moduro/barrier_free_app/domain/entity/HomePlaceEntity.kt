package com.moduro.barrier_free_app.domain.entity

data class HomePlaceEntity(
    val placeId: Long,
    val placeType: String,
    val name: String,
    val region: String,
    val description: String,
    val facility: List<Int>,
    val imageType: Int,
)
