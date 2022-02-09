package com.example.weather.data

import java.lang.Exception

sealed class Resource<out T> {
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(val errorMsg: String?) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$errorMsg]"
            is Loading -> "Loading"
        }
    }
}