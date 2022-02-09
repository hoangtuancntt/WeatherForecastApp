package com.example.weather.data.source.remote

import com.example.weather.data.Resource
import com.example.weather.data.error.ErrorResponse
import com.example.weather.data.models.RemoteForecast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RemoteDataSourceImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val ioDispatcher: CoroutineContext
) : RemoteDataSource {
    override suspend fun getForecastByLocation(location: String?): Resource<List<RemoteForecast>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result =
                    weatherService.getForecast(location, 7, "60c6fbeb4b93ac653c492ba806fc346d")
                if (result.isSuccessful) Resource.Success(result.body()?.list)
                else {
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorResponse: ErrorResponse? =
                        Gson().fromJson(result.errorBody()!!.charStream(), type)
                    Resource.Error(errorMsg = errorResponse?.message)
                }
            } catch (ex: Exception) {
                Resource.Error(errorMsg = ex.message)
            }
        }
}