package com.example.weather.di

import com.example.weather.data.source.repository.WeatherRepositoryImpl
import com.example.weather.data.source.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(weatherRepository: WeatherRepositoryImpl) : WeatherRepository
}