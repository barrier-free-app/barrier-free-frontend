package com.moduro.barrier_free_app.data.dto.response

data class LocationNamesResponseDto(
    val documents: List<Document>
)

data class Document(
    val address: Address?
)

data class Address(
    val address_name: String,
    val region_2depth_name: String, // 구
    val region_3depth_name: String  // 동
)
