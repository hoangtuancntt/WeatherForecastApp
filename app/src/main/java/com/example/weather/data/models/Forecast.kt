package com.example.weather.data.models

data class Forecast(
    val dt: Long,
    val temp: Temperature?,
    val pressure: Int,
    val humidity: Int,
    val weather: List<Weather>?
)