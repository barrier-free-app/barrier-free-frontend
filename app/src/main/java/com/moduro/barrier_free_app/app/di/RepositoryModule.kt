package com.moduro.barrier_free_app.app.di


import com.moduro.barrier_free_app.data.repositoryimpl.ExampleRepositoryImpl
import com.moduro.barrier_free_app.domain.repository.ExampleRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExampleRepository(exampleRepositoryImpl: ExampleRepositoryImpl): ExampleRepository

}