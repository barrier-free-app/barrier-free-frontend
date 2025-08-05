package com.moduro.barrier_free_app.domain.entity

data class UserEntity(
    val userId: Long,
    val email: String,
    val nickname: String,
    val userType: String,
    val userFacilities: List<FacilityEntity>,
    val socialType: String
)

