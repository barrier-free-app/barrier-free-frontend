package com.moduro.barrier_free_app.app.di


import com.moduro.barrier_free_app.data.repositoryimpl.AirKoreaRepositoryImpl
import com.moduro.barrier_free_app.data.repositoryimpl.ExampleRepositoryImpl
import com.moduro.barrier_free_app.data.repositoryimpl.HomeRepositoryImpl
import com.moduro.barrier_free_app.data.repositoryimpl.LocationNameRepositoryImpl
import com.moduro.barrier_free_app.data.repositoryimpl.LocationTempRepositoryImpl
import com.moduro.barrier_free_app.domain.repository.AirKoreaRepository
import com.moduro.barrier_free_app.domain.repository.ExampleRepository
import com.moduro.barrier_free_app.domain.repository.HomeRepository
import com.moduro.barrier_free_app.domain.repository.LocationNameRepository
import com.moduro.barrier_free_app.domain.repository.LocationTempRepository

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

    @Binds
    @Singleton
    abstract fun bindAirKoreaRepository(airKoreaRepositoryImpl : AirKoreaRepositoryImpl): AirKoreaRepository

    @Binds
    @Singleton
    abstract fun bindLocationTempRepository(locationTempRepositoryImpl : LocationTempRepositoryImpl): LocationTempRepository

    @Binds
    @Singleton
    abstract fun bindLocationNameRepository(locationNameRepositoryImpl: LocationNameRepositoryImpl) : LocationNameRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl) : HomeRepository

}