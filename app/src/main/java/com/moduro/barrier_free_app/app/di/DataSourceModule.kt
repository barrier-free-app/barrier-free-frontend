package com.moduro.barrier_free_app.app.di

import com.moduro.barrier_free_app.data.datasource.AirKoreaDataSource
import com.moduro.barrier_free_app.data.datasource.ExampleDataSource
import com.moduro.barrier_free_app.data.datasource.HomeDataSource
import com.moduro.barrier_free_app.data.datasource.LocationNameDataSource
import com.moduro.barrier_free_app.data.datasource.LocationTempDataSource
import com.moduro.barrier_free_app.data.datasource.MapDataSource
import com.moduro.barrier_free_app.data.datasource.PlaceReportDataSource
import com.moduro.barrier_free_app.data.datasource.SearchDataSource
import com.moduro.barrier_free_app.data.datasourceimpl.AirKoreaDataSourceImpl
import com.moduro.barrier_free_app.data.datasourceimpl.ExampleDataSourceImpl
import com.moduro.barrier_free_app.data.datasourceimpl.HomeDataSourceImpl
import com.moduro.barrier_free_app.data.datasourceimpl.LocationNameDataSourceImpl
import com.moduro.barrier_free_app.data.datasourceimpl.LocationTempDataSourceImpl
import com.moduro.barrier_free_app.data.datasourceimpl.MapDataSourceImpl
import com.moduro.barrier_free_app.data.datasourceimpl.PlaceReportDataSourceImpl
import com.moduro.barrier_free_app.data.datasourceimpl.SearchDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun bindAirKoreaDataSource(airKoreaDataSourceImpl: AirKoreaDataSourceImpl): AirKoreaDataSource

    @Binds
    @Singleton
    abstract fun bindLocationTempDataSource(locationTempDataSourceImpl: LocationTempDataSourceImpl) : LocationTempDataSource

    @Binds
    @Singleton
    abstract fun bindLocationNameDataSource(locationNameDataSourceImpl: LocationNameDataSourceImpl) : LocationNameDataSource

    @Binds
    @Singleton
    abstract fun bindHomeDataSource(homeDataSourceImpl: HomeDataSourceImpl) : HomeDataSource

    @Binds
    @Singleton
    abstract fun bindMapDataSource(mapDataSourceImpl: MapDataSourceImpl) : MapDataSource

    @Binds
    @Singleton
    abstract fun bindPlaceReportDataSource(placeReportDataSourceImpl: PlaceReportDataSourceImpl) : PlaceReportDataSource

    @Binds
    @Singleton
    abstract fun bindSearchDataSource(searchDataSourceImpl: SearchDataSourceImpl) : SearchDataSource

}