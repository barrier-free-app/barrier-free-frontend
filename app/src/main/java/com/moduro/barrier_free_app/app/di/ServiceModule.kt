package com.moduro.barrier_free_app.app.di

import com.moduro.barrier_free_app.data.service.AirKoreaApiService
import com.moduro.barrier_free_app.data.service.ExampleApiService
import com.moduro.barrier_free_app.data.service.HomeApiService
import com.moduro.barrier_free_app.data.service.LocationNameApiService
import com.moduro.barrier_free_app.data.service.LocationTempApiService
import com.moduro.barrier_free_app.data.service.MapApiService
import com.moduro.barrier_free_app.data.service.PlaceReportApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideExampleService(
        @ModuroRetrofit retrofit: Retrofit
    ): ExampleApiService = retrofit.create(ExampleApiService::class.java)

    @Provides
    @Singleton
    fun provideAirKoreaService(
        @AirKoreaRetrofit retrofit: Retrofit
    ): AirKoreaApiService = retrofit.create(AirKoreaApiService::class.java)

    @Provides
    @Singleton
    fun provideLocationTempService(
        @LocationTempRetrofit retrofit: Retrofit
    ) : LocationTempApiService = retrofit.create(LocationTempApiService::class.java)

    @Provides
    @Singleton
    fun provideLocationNameService(
        @LocationNameRetrofit retrofit: Retrofit
    ) : LocationNameApiService = retrofit.create(LocationNameApiService::class.java)

    @Provides
    @Singleton
    fun provideHomeService(
        @ModuroRetrofit retrofit: Retrofit
    ): HomeApiService = retrofit.create(HomeApiService::class.java)

    @Provides
    @Singleton
    fun provideMapService(
        @ModuroRetrofit retrofit: Retrofit
    ) : MapApiService = retrofit.create(MapApiService::class.java)

    @Provides
    @Singleton
    fun providePlaceReportService(
        @ModuroRetrofit retrofit: Retrofit
    ) : PlaceReportApiService = retrofit.create(PlaceReportApiService::class.java)

}
