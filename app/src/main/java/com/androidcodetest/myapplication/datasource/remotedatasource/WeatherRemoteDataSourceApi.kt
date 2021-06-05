package com.androidcodetest.myapplication.datasource.remotedatasource

import com.androidcodetest.myapplication.data.WeatherByCity
import retrofit2.Response
import retrofit2.http.GET

interface WeatherRemoteDataSourceApi {
    @GET("/api/v1")
    suspend fun getWeatherByCity(city:String): Response<List<WeatherByCity>>


}