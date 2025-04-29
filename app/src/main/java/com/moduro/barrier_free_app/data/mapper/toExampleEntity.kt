package com.moduro.barrier_free_app.data.mapper

import com.moduro.barrier_free_app.data.dto.response.ResponseGetExampleDto
import com.moduro.barrier_free_app.domain.entity.ExampleEntity

fun ResponseGetExampleDto.toExampleEntity() = ExampleEntity(
    email, firstName, avatar
)