package com.androidcodetest.myapplication.datasource.remotedatasource

import com.androidcodetest.myapplication.data.WeatherByCity
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRemoteDataSourceApi {
    @GET("/data/2.5/weather")
    suspend fun getWeatherByCity(@Query("q")city:String): WeatherByCity

    @GET("/data/2.5/weather")
    suspend fun getWeatherByZipCode(@Query("zip")zipCode:String): WeatherByCity


}