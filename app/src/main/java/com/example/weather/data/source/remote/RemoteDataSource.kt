package com.example.weather.data.source.remote

import com.example.weather.data.Resource
import com.example.weather.data.models.Forecast
import com.example.weather.data.models.RemoteForecast
import com.example.weather.data.models.RemoteWeatherForecast

internal interface RemoteDataSource {
    suspend fun getForecastByLocation(location: String?) : Resource<List<RemoteForecast>>
}