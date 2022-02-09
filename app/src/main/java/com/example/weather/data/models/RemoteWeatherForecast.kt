package com.example.weather.data.models

data class RemoteWeatherForecast(
    val city: City,
    val cod: String,
    val message: Float,
    val cnt: Int,
    val list: List<RemoteForecast>
)


