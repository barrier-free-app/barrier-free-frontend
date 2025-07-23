package com.moduro.barrier_free_app.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModuroBaseResponse<T>(
    @SerialName("isSuccess") val isSuccess: Boolean,
    @SerialName("httpStatus") val httpStatus: String,
    @SerialName("code") val code: String,
    @SerialName("message") val message: String,
    @SerialName("result") val result: T? = null
)