package com.moduro.barrier_free_app.domain.entity

data class SearchPlaceEntity(
    val placeId: Long,
    val placeType: String,
    val facilities: List<Int>,
    val description : String,
    val region: String,
    val name: String,
    val imageType: Int
)

data class SearchPlaceResultEntity(
    val placeSearchResponses : List<SearchPlaceEntity>,
    val page : Int,
    val size : Int,
    val totalPages : Int,
    val totalElements: Int,
    val hasNext : Boolean
)