package com.moduro.barrier_free_app.data.datasourceimpl

import com.moduro.barrier_free_app.data.datasource.ExampleDataSource
import com.moduro.barrier_free_app.data.dto.ExampleBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseGetExampleDto
import com.moduro.barrier_free_app.data.service.ExampleApiService
import javax.inject.Inject

class ExampleDataSourceImpl @Inject constructor(
    private val exampleApiService: ExampleApiService
) : ExampleDataSource {
    override suspend fun getUsers(page: Int): ExampleBaseResponse<List<ResponseGetExampleDto>> {
        return exampleApiService.getUsers(page)
    }
}