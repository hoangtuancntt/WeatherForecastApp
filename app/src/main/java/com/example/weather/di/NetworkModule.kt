package com.example.weather.di

import com.example.weather.data.source.remote.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideInstantWeatherApiService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }
}