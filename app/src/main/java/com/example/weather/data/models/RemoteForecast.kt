package com.example.weather.data.models

data class RemoteForecast(
    val dt: Long = 0L,
    val sunrise: Long = 0L,
    val sunset: Long = 0L,
    val temp: Temperature? = null,
    val feels_like: FeelLike? = null,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val weather: List<Weather>? = null,
    val speed: Float = 0f,
    val deg: Int = 0,
    val gust: Float = 0f,
    val clouds: Int = 0,
    val pop: Float = 0f
)