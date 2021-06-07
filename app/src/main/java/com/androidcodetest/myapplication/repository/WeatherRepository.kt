package com.androidcodetest.myapplication.repository

import android.content.Context
import com.androidcodetest.myapplication.R
import com.androidcodetest.myapplication.data.WeatherByCity
import com.androidcodetest.myapplication.data.respond.GetWeatherByCityOrZipRespond
import com.androidcodetest.myapplication.data.respond.GetWeathersOrderInRecentRespond
import com.androidcodetest.myapplication.datasource.localdatasource.database.WeatherDao
import com.androidcodetest.myapplication.datasource.remotedatasource.WeatherRemoteDataSourceApi
import com.google.gson.Gson
import kotlinx.coroutines.flow.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter
import java.util.*


interface IWeatherRepository {
    suspend fun getWeathersOrderInLastTouch(): Flow<Result<GetWeathersOrderInRecentRespond>>?
    suspend fun getWeatherByCity(city: String): Flow<Result<GetWeatherByCityOrZipRespond>>?
    suspend fun getWeatherByZip(zip: String): Flow<Result<GetWeatherByCityOrZipRespond>>?
    suspend fun updateWeatherByCity(city: String)
    suspend fun updateLastTouchByCity(city: String)
    suspend fun updateLastTouchByZipCode(zip: String)
}


class Weatherepository(var api: WeatherRemoteDataSourceApi, var context: Context, var dao: WeatherDao) : IWeatherRepository {
    override suspend fun getWeathersOrderInLastTouch(): Flow<Result<GetWeathersOrderInRecentRespond>>? {
        var weathersListener =dao.getAllByLastSearchFlow()

        return weathersListener?.map {
            var getWeathersOrderInRecentRespond = GetWeathersOrderInRecentRespond()
            getWeathersOrderInRecentRespond.weathers = it
            Result.success(getWeathersOrderInRecentRespond)

        }
    }

    override suspend fun getWeatherByCity(city: String): Flow<Result<GetWeatherByCityOrZipRespond>>? {
        updateWeatherByCity(city)
        var weatherListener =  dao.getByCityNameFlow("London")

        return weatherListener?.map {
            var getWeatherByCityOrZipRespond = GetWeatherByCityOrZipRespond()
            getWeatherByCityOrZipRespond.weatherByCity = it
            Result.success(getWeatherByCityOrZipRespond)

        }
    }

    override suspend fun getWeatherByZip(zipCode: String): Flow<Result<GetWeatherByCityOrZipRespond>>? {
        updateWeatherByZipCode(zipCode)
        var weatherListener =  dao.getByZipCodeFlow(zipCode)

        return weatherListener?.map {
            var getWeatherByCityOrZipRespond = GetWeatherByCityOrZipRespond()
            getWeatherByCityOrZipRespond.weatherByCity = it
            Result.success(getWeatherByCityOrZipRespond)

        }
    }

    override suspend fun updateWeatherByCity(city: String) {
      var  weatherByCity = api.getWeatherByCity(city).body()
        if (weatherByCity != null) {
            dao.insert(weatherByCity)
        }
    }

    override suspend fun updateLastTouchByCity(city: String) {
       var w =  dao.getByCityName(city)
        dao.updateTimeByID(w._id, Calendar.getInstance().getTime().toString())
    }

    override suspend fun updateLastTouchByZipCode(zip: String) {
        var w =  dao.getByZipCode(zip)
        dao.updateTimeByID(w._id, Calendar.getInstance().getTime().toString())
    }

    suspend fun updateWeatherByZipCode(city: String) {
        var  weatherByCity = api.getWeatherByCity(city).body()
        if (weatherByCity != null) {
            dao.insert(weatherByCity)
        }
    }

}

class mockWeatherRepository(var context: Context, var dao: WeatherDao) : IWeatherRepository {
    override suspend fun getWeathersOrderInLastTouch(): Flow<Result<GetWeathersOrderInRecentRespond>>? {
        updateWeatherByCity("city")

        var weathersListener =dao.getAllByLastSearchFlow()

        return weathersListener?.map {
            var getWeathersOrderInRecentRespond = GetWeathersOrderInRecentRespond()
            getWeathersOrderInRecentRespond.weathers = it
            Result.success(getWeathersOrderInRecentRespond)

        }

    }

    override suspend fun getWeatherByCity(city: String): Flow<Result<GetWeatherByCityOrZipRespond>>? {
           updateWeatherByCity("city")
        var londonWeatherListener =  dao.getByCityNameFlow("London")

        return londonWeatherListener?.map {
            var getWeatherByCityOrZipRespond = GetWeatherByCityOrZipRespond()
            getWeatherByCityOrZipRespond.weatherByCity = it
            Result.success(getWeatherByCityOrZipRespond)

        }
    }

    override suspend fun getWeatherByZip(zip: String): Flow<Result<GetWeatherByCityOrZipRespond>>? {

        updateWeatherByCity("city")
        var londonWeatherListener =  dao.getByCityNameFlow("London")

        return londonWeatherListener?.map {
            var getWeatherByCityOrZipRespond = GetWeatherByCityOrZipRespond()
            getWeatherByCityOrZipRespond.weatherByCity = it
            Result.success(getWeatherByCityOrZipRespond)

        }
    }

    override suspend fun updateWeatherByCity(city: String) {
        var londonWeatherListener =  dao.getByCityNameFlow("London")
        var weatherByCity:WeatherByCity?
        if(londonWeatherListener !=null){
             weatherByCity = londonWeatherListener.firstOrNull()
            if (weatherByCity?.main?.temp  == "1000") {
                weatherByCity.main.temp   = "1001"
            } else {
                weatherByCity?.main?.temp  = "1000"
            }
        }else{

            val _is = context.getResources().openRawResource(R.raw.testweatherjson)
            val writer = StringWriter()
            val buffer = CharArray(1024)
            try {
                val reader = BufferedReader(InputStreamReader(_is, "UTF-8"))
                var n: Int = 0
                while (reader.read(buffer).also({ n = it }) != -1) {
                    writer.write(buffer, 0, n)
                }
            } finally {
                _is.close()
            }

            val jsonString: String = writer.toString()
             weatherByCity = Gson().fromJson(jsonString, WeatherByCity::class.java)
        }
        if (weatherByCity != null) {
            dao.insert(weatherByCity)
        }
    }

    override suspend fun updateLastTouchByCity(city: String) {
        updateWeatherByCity("city")
        var w =  dao.getByCityName("London")
        dao.updateTimeByID(w._id, Calendar.getInstance().getTime().toString())
    }

    override suspend fun updateLastTouchByZipCode(zip: String) {
        var w =  dao.getByZipCode(zip)
        dao.updateTimeByID(w._id, Calendar.getInstance().getTime().toString())
    }
}