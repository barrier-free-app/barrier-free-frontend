package com.moduro.barrier_free_app.domain.entity

data class HomePlaceEntity(
    val type: Int,
    val name: String,
    val location: String,
    val description: String,
    val facilities: List<String>
)