package com.example.weather.data.mapper

import com.example.weather.data.models.Forecast
import com.example.weather.data.models.RemoteForecast

class RemoteForecastMapper : BaseMapper<List<RemoteForecast>, List<Forecast>> {
    override fun transformToDomain(type: List<RemoteForecast>): List<Forecast> {
        return type.map { remoteForecast ->
            Forecast(
                remoteForecast.dt,
                remoteForecast.temp,
                remoteForecast.pressure,
                remoteForecast.humidity,
                remoteForecast.weather
            )
        }
    }

    override fun transformToDto(type: List<Forecast>): List<RemoteForecast> {
        return type.map { forecast ->
            RemoteForecast(
                dt = forecast.dt,
                temp = forecast.temp,
                pressure = forecast.pressure,
                humidity = forecast.humidity,
                weather = forecast.weather
            )
        }
    }
}