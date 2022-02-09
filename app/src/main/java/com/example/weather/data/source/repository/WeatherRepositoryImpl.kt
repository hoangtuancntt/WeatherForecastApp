package com.example.weather.data.source.repository

import com.example.weather.data.Resource
import com.example.weather.data.mapper.RemoteForecastMapper
import com.example.weather.data.models.Forecast
import com.example.weather.data.models.RemoteForecast
import com.example.weather.data.models.RemoteWeatherForecast
import com.example.weather.data.source.remote.RemoteDataSourceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class WeatherRepositoryImpl @Inject constructor(
    private val remoteData: RemoteDataSourceImpl,
    private val ioDispatcher: CoroutineContext
) :
    WeatherRepository {
    override suspend fun getForecastByLocation(location: String?): Resource<List<Forecast>> =
        withContext(ioDispatcher) {
            val mapper = RemoteForecastMapper()
            when (val response = remoteData.getForecastByLocation(location)) {
                is Resource.Success -> {
                    if (response.data != null)
                        Resource.Success(mapper.transformToDomain(response.data))
                    else Resource.Success(null)
                }
                is Resource.Error -> {
                    Resource.Error(response.errorMsg)
                }
                else -> Resource.Loading
            }
        }
}