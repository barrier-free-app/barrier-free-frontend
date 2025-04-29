package com.moduro.barrier_free_app.app.di

import com.moduro.barrier_free_app.data.datasource.ExampleDataSource
import com.moduro.barrier_free_app.data.datasourceimpl.ExampleDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindExampleDataSource(exampleDataSourceImpl: ExampleDataSourceImpl): ExampleDataSource

}