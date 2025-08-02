package com.moduro.barrier_free_app.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseReviewListDto(
    @SerialName("result") val result: ReviewListResult
)

@Serializable
data class ReviewListResult(
    @SerialName("page") val page: Int,
    @SerialName("size") val size: Int,
    @SerialName("totalPages") val totalPages: Int,
    @SerialName("totalElements") val totalElements: Int,
    @SerialName("hasNext") val hasNext: Boolean,
    @SerialName("reviews") val reviews: List<ReviewItem>
)

@Serializable
data class ReviewItem(
    @SerialName("placeId") val placeId: Long,
    @SerialName("placeName") val placeName: String,
    @SerialName("placeType") val placeType: String,
    @SerialName("imageType") val imageType: Int,
    @SerialName("content") val content: String,
    @SerialName("rating") val rating: Double,
    @SerialName("reviewImageUrls") val reviewImageUrls: List<String>
)
