package com.androidcodetest.myapplication.repository

import android.content.Context
import com.androidcodetest.myapplication.data.respond.GetWeatherByCityRespond
import com.androidcodetest.myapplication.datasource.localdatasource.database.WeatherDao
import com.androidcodetest.myapplication.datasource.remotedatasource.WeatherRemoteDataSourceApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception


interface IWeatherRepository {
    suspend fun getWeatherByCity(city: String): Flow<Result<GetWeatherByCityRespond>?>
    fun getWeatherByCityCached(city: String): Result<GetWeatherByCityRespond>?
}


class Weatherepository(api: WeatherRemoteDataSourceApi, context: Context, dao : WeatherDao) : IWeatherRepository {

    override suspend fun getWeatherByCity(city: String): Flow<Result<GetWeatherByCityRespond>?> {
        return flow {
            emit(fetchTrendingMoviesCached())
            emit(Result.loading())
            val result = movieRemoteDataSource.fetchTrendingMovies()

            //Cache to database if response is successful
            if (result.status == Result.Status.SUCCESS) {
                result.data?.results?.let { it ->
                    movieDao.deleteAll(it)
                    movieDao.insertAll(it)
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override fun getWeatherByCityCached(city: String): Result<GetWeatherByCityRespond>? {
        weatherDao.get
    }

    private fun fetchTrendingMoviesCached(): Result<GetWeatherByCityRespond>? =
        movieDao.getAll()?.let {
            Result.success(TrendingMovieResponse(it))
        }

    suspend fun fetchMovie(id: Int): Flow<Result<MovieDesc>> {
        return flow {
            emit(Result.loading())
            emit(movieRemoteDataSource.fetchMovie(id))
        }.flowOn(Dispatchers.IO)
    }
}

class mokeWeatherRepository {

    fun echoString(string: String): Result<out String> {
        return if (string.isEmpty()) {
            Failure(Exception("Error"))
        } else {
            Success(string)
        }
    }

}