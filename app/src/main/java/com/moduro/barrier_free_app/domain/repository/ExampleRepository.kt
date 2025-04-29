package com.moduro.barrier_free_app.domain.repository

import com.moduro.barrier_free_app.domain.entity.ExampleEntity

interface ExampleRepository {
    suspend fun getUsers(page: Int): Result<List<ExampleEntity>>
}