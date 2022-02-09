package com.example.weather.di

import com.example.weather.BuildConfig
import com.example.weather.utils.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        okHttpClientBuilder: OkHttpClient.Builder,
        converterFactory: GsonConverterFactory
    ) : Retrofit {
        val headerInterceptor = Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }

        val logger = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            logger.apply { level = HttpLoggingInterceptor.Level.BODY }
        else logger.apply { level = HttpLoggingInterceptor.Level.NONE }

        okHttpClientBuilder.addInterceptor(headerInterceptor)
        okHttpClientBuilder.addInterceptor(logger)
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(converterFactory)
            .build()
    }
}