package com.androidcodetest.myapplication.datasource.localdatasource.database

import androidx.room.*
import com.androidcodetest.myapplication.data.WeatherByCity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather ")
    fun getAll(): Flow<List<WeatherByCity>>?


    @Query("SELECT * FROM weather order by lastsearch DESC")
    fun getAllByLastSearchFlow(): Flow<List<WeatherByCity>>?

    @Query("SELECT * FROM weather where name = :city")
    fun getByCityNameFlow(city:String):Flow<  WeatherByCity>?


    @Query("SELECT * FROM weather where name = :city")
    fun getByCityName(city:String):  WeatherByCity

    @Query("SELECT * FROM weather where zip = :zip")
    fun getByZipCodeFlow(zip:String):Flow<  WeatherByCity>?


    @Query("SELECT * FROM weather where zip = :zip")
    fun getByZipCode(zip:String):  WeatherByCity


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weatherByCitys: List<WeatherByCity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherByCity: WeatherByCity)


    @Query("UPDATE weather SET lastSearch = :time WHERE id = :tid")
    fun updateTimeByID(tid: Int,time:String ):Int

    @Delete
    fun delete(weatherByCity: WeatherByCity)

    @Delete
    fun deleteAll(weatherByCitys: List<WeatherByCity>)


}