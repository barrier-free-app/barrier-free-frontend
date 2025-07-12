package com.moduro.barrier_free_app.app.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ModuroRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AirKoreaRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationTempRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocationNameRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccessToken

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserPreferences
