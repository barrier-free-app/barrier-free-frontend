package com.moduro.barrier_free_app.app.di

import com.moduro.barrier_free_app.data.service.AirKoreaApiService
import com.moduro.barrier_free_app.data.service.AuthApiService
import com.moduro.barrier_free_app.data.service.ExampleApiService
import com.moduro.barrier_free_app.data.service.HomeApiService
import com.moduro.barrier_free_app.data.service.FavoriteToggleApiService
import com.moduro.barrier_free_app.data.service.LocationNameApiService
import com.moduro.barrier_free_app.data.service.LocationTempApiService
import com.moduro.barrier_free_app.data.service.MapApiService
import com.moduro.barrier_free_app.data.service.PlaceReportApiService
import com.moduro.barrier_free_app.data.service.SearchApiService
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

    @Provides
    @Singleton
    fun provideFavoriteToggleApiService(
        @ModuroRetrofit retrofit: Retrofit
    ): FavoriteToggleApiService = retrofit.create(FavoriteToggleApiService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(
        @ModuroRetrofit retrofit: Retrofit
    ) : AuthApiService = retrofit.create(AuthApiService::class.java)
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

    @Provides
    @Singleton
    fun provideSearchService(
        @ModuroRetrofit retrofit: Retrofit
    ) : SearchApiService = retrofit.create(SearchApiService::class.java)

}
