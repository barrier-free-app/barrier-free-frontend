package com.moduro.barrier_free_app.app.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.moduro.barrier_free_app.BuildConfig
import com.moduro.barrier_free_app.app.interceptor.LocationNameTokenInterceptor
import com.moduro.barrier_free_app.app.interceptor.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = false
        encodeDefaults = true
    }

    // 미세먼지 API용 OkHttpClient
    @Provides
    @Singleton
    @Named("airKoreaClient")
    fun provideRegionOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // 로그 추가
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // 백엔드 API용 OkHttpClient
    @Provides
    @Singleton
    @Named("appClient")
    fun provideAppOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // 로그 추가
            .addInterceptor(tokenInterceptor) // 토큰 인터셉터 추가
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // 법정동 구역 API용 OkHttpClient
    @Provides
    @Singleton
    @Named("locationNameClient")
    fun provideLocationNameOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: LocationNameTokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // 로그 추가
            .addInterceptor(tokenInterceptor) // 토큰 인터셉터 추가
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    //백엔드용 retrofit
    @Singleton
    @Provides
    @ModuroRetrofit
    fun provideRetrofit(@Named("appClient") okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .baseUrl("")
            .build()


    //미세먼지용 retrofit
    @Singleton
    @Provides
    @AirKoreaRetrofit
    fun provideAirKoreaRetrofit(
        @Named("airKoreaClient") okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AIR_KOREA_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    //LocationTemp 용 retrofit
    @Singleton
    @Provides
    @LocationTempRetrofit
    fun provideLocationTempRetrofit(
        @Named("airKoreaClient") okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.LOCATION_TEMP_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    //LocationName 용 retrofit
    @Singleton
    @Provides
    @LocationNameRetrofit
    fun provideLocationNameRetrofit(
        @Named("locationNameClient") okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.LOCATION_NAME_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}