package com.example.weather.data.source.remote

import com.example.weather.data.models.RemoteWeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("daily")
    suspend fun getForecast(@Query("q") location: String?, @Query("cnt") cnt: Int, @Query("appid") appid: String) : Response<RemoteWeatherForecast>
}