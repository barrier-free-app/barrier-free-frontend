package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.ExampleBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseGetExampleDto

interface ExampleDataSource {
    suspend fun getUsers(page: Int): ExampleBaseResponse<List<ResponseGetExampleDto>>
}