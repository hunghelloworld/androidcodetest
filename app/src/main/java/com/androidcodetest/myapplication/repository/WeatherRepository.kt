package com.androidcodetest.myapplication.repository

import android.content.Context
import android.util.Log
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
    suspend fun updateWeatherByZipCode(zip: String)
}


class WeatherRepository(
    var api: WeatherRemoteDataSourceApi,
    var dao: WeatherDao
) : IWeatherRepository {
    override suspend fun getWeathersOrderInLastTouch(): Flow<Result<GetWeathersOrderInRecentRespond>>? {
        var weathersListener = dao.getAllByLastSearchFlow()

        return weathersListener?.map {
            var getWeathersOrderInRecentRespond = GetWeathersOrderInRecentRespond()
            getWeathersOrderInRecentRespond.weathers = it
            Result.success(getWeathersOrderInRecentRespond)

        }
    }

    override suspend fun getWeatherByCity(city: String): Flow<Result<GetWeatherByCityOrZipRespond>>? {
        updateWeatherByCity(city)
        var weatherListener = dao.getByCityNameFlow("London")

        return weatherListener?.map {
            var getWeatherByCityOrZipRespond = GetWeatherByCityOrZipRespond()
            getWeatherByCityOrZipRespond.weatherByCity = it
            Result.success(getWeatherByCityOrZipRespond)

        }
    }

    override suspend fun getWeatherByZip(zipCode: String): Flow<Result<GetWeatherByCityOrZipRespond>>? {
        updateWeatherByZipCode(zipCode)
        var weatherListener = dao.getByZipCodeFlow(zipCode)

        return weatherListener?.map {
            var getWeatherByCityOrZipRespond = GetWeatherByCityOrZipRespond()
            getWeatherByCityOrZipRespond.weatherByCity = it
            Result.success(getWeatherByCityOrZipRespond)

        }
    }

    override suspend fun updateWeatherByCity(city: String) {
        var weatherByCity = api.getWeatherByCity(city)
        Log.d("updateWeatherByCity", " after api ")
        dao.insert(weatherByCity)


        var wAny = dao.getByCityName(city)
        if (wAny.size == 0) return
        var w = wAny.get(0)
        Log.d("updateLastTouchByCity", " w._id = " + w._id)
    }

    override suspend fun updateLastTouchByCity(city: String) {
        Log.d("updateLastTouchByCity", " enter ")
        var wAny = dao.getByCityName(city)
        if (wAny.size == 0) return
        var w = wAny.get(0)
        Log.d("updateLastTouchByCity", " w._id = " + w._id)
        dao.updateTimeByID(w._id, Calendar.getInstance().getTime().toString())
    }

    override suspend fun updateLastTouchByZipCode(zip: String) {
        var wAny = dao.getByZipCode(zip)
        if (wAny.size == 0) return
        var w = wAny.get(0)
        Log.d("updateLastTouchByZip", " w._id = " + w._id)
        dao.updateTimeByID(w._id, Calendar.getInstance().getTime().toString())
    }

    override suspend fun updateWeatherByZipCode(zipCode: String) {
        var weatherByCity = api.getWeatherByZipCode(zipCode)
        Log.d("updateWeatherByZip", " after api ")
        if (weatherByCity != null) {
            dao.insert(weatherByCity)
        }
    }

}

class mockWeatherRepository(var context: Context, var dao: WeatherDao) : IWeatherRepository {
    override suspend fun getWeathersOrderInLastTouch(): Flow<Result<GetWeathersOrderInRecentRespond>>? {
        updateWeatherByCity("city")

        var weathersListener = dao.getAllByLastSearchFlow()

        return weathersListener?.map {
            var getWeathersOrderInRecentRespond = GetWeathersOrderInRecentRespond()
            getWeathersOrderInRecentRespond.weathers = it
            Result.success(getWeathersOrderInRecentRespond)

        }

    }

    override suspend fun getWeatherByCity(city: String): Flow<Result<GetWeatherByCityOrZipRespond>>? {
        updateWeatherByCity("city")
        var londonWeatherListener = dao.getByCityNameFlow("London")

        return londonWeatherListener?.map {
            var getWeatherByCityOrZipRespond = GetWeatherByCityOrZipRespond()
            getWeatherByCityOrZipRespond.weatherByCity = it
            Result.success(getWeatherByCityOrZipRespond)

        }
    }

    override suspend fun getWeatherByZip(zip: String): Flow<Result<GetWeatherByCityOrZipRespond>>? {

        updateWeatherByCity("city")
        var londonWeatherListener = dao.getByCityNameFlow("London")

        return londonWeatherListener?.map {
            var getWeatherByCityOrZipRespond = GetWeatherByCityOrZipRespond()
            getWeatherByCityOrZipRespond.weatherByCity = it
            Result.success(getWeatherByCityOrZipRespond)

        }
    }

    override suspend fun updateWeatherByCity(city: String) {
        Log.d("updateWeatherByCity","enter function")
        var londonWeatherListener = dao.getByCityName("London")
        var weatherByCity: WeatherByCity?
        if (londonWeatherListener.size>0) {
            weatherByCity = londonWeatherListener.firstOrNull()
            if (weatherByCity?.main?.temp == "1000") {
                weatherByCity.main.temp = "1001"
            } else {
                weatherByCity?.main?.temp = "1000"
            }
        } else {

            Log.d("updateWeatherByCity","add fake item to db")
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


    override suspend fun updateWeatherByZipCode(zip: String) {
        Log.d("updateWeatherByZipCode","enter function")
        var londonWeatherListener = dao.getByCityName("London")
        var weatherByCity: WeatherByCity?
        if (londonWeatherListener .size>0) {
            weatherByCity = londonWeatherListener.firstOrNull()
            if (weatherByCity?.main?.temp == "1000") {
                weatherByCity.main.temp = "1001"
            } else {
                weatherByCity?.main?.temp = "1000"
            }
        } else {

            Log.d("updateWeatherByZipCode","add fake item to db")
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
        var wAny = dao.getByCityName("London")
        if (wAny.size == 0) return
        var w = wAny.get(0)
        Log.d("updateLastTouchByCity", " w._id = " + w._id)

        dao.updateTimeByID(w._id, Calendar.getInstance().getTime().toString())
    }

    override suspend fun updateLastTouchByZipCode(zip: String) {
        updateWeatherByCity("city")
        var wAny = dao.getByCityName("London")
        if (wAny.size == 0) return
        var w = wAny.get(0)
        Log.d("updateLastTouchByZip", " w._id = " + w._id)

        dao.updateTimeByID(w._id, Calendar.getInstance().getTime().toString())
    }
}