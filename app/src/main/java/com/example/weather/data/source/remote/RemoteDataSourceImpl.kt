package com.example.weather.data.source.remote

import com.example.weather.data.Resource
import com.example.weather.data.error.ErrorResponse
import com.example.weather.data.models.RemoteForecast
import com.example.weather.utils.NetworkConnectivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RemoteDataSourceImpl @Inject constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity,
    private val ioDispatcher: CoroutineContext
) : RemoteDataSource {
    override suspend fun getForecastByLocation(location: String?): Resource<List<RemoteForecast>> =
        withContext(ioDispatcher) {
            return@withContext try {
                if (!networkConnectivity.isConnected()) {
                    Resource.Error(errorMsg = "No Internet Connection")
                }
                val weatherService = serviceGenerator.createService(WeatherService::class.java)
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