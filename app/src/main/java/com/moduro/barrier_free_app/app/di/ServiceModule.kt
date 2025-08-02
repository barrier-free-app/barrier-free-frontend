package com.moduro.barrier_free_app.app.di

import com.moduro.barrier_free_app.data.service.AirKoreaApiService
import com.moduro.barrier_free_app.data.service.ExampleApiService
import com.moduro.barrier_free_app.data.service.LocationNameApiService
import com.moduro.barrier_free_app.data.service.LocationTempApiService
import com.moduro.barrier_free_app.data.service.MypageApiService
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
    fun provideMypageApiService(
        @ModuroRetrofit retrofit: Retrofit
    ): MypageApiService = retrofit.create(MypageApiService::class.java)
}
