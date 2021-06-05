package com.androidcodetest.myapplication.datasource.localdatasource.database

import androidx.room.*
import com.androidcodetest.myapplication.data.WeatherByCity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather order by lastsearch DESC")
    fun getAll(): List<WeatherByCity>?

    @Query("SELECT * FROM weather where order  by lastsearch DESC")
    fun getByCityName(city:String): WeatherByCity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weatherByCitys: List<WeatherByCity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherByCity: WeatherByCity)

    @Delete
    fun delete(weatherByCity: WeatherByCity)

    @Delete
    fun deleteAll(weatherByCitys: List<WeatherByCity>)


}