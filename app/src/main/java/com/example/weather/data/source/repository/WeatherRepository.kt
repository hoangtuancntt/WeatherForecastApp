package com.example.weather.data.source.repository

import com.example.weather.data.Resource
import com.example.weather.data.models.Forecast
import com.example.weather.data.models.RemoteWeatherForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getForecastByLocation(location: String?) : Resource<List<Forecast>>
}