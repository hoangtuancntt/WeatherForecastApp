package com.example.weather.data.models

data class City(
    val id: Long,
    val name: String,
    val coord: Coordinate,
    val country: String,
    val population: Int,
    val timezone: Long
)
