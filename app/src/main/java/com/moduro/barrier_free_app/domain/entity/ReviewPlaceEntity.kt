package com.moduro.barrier_free_app.domain.entity

data class ReviewPlaceEntity(
    val placeId: Long,
    val placeName: String,
    val placeType: String,
    val imageType: Int,
    val content: String,
    val rating: Double,
    val reviewImageUrls: List<String>
)
