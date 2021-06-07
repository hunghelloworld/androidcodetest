package com.androidcodetest.myapplication.data

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Entity(tableName = "Weather")
@Parcelize
data class WeatherByCity (
        @PrimaryKey(autoGenerate = true)
        var _id:Int,
        @ColumnInfo(name = "zip", defaultValue = "000000")
        var zip:String,
        @ColumnInfo(name = "lastSearch", typeAffinity = ColumnInfo.INTEGER, defaultValue = "0")
        var lastsearch:Integer,
        var coord: Coord, var weather:ArrayList< Weather>, var base: String,
        var main: Main, var wind: Wind,

        @ColumnInfo(name = "clouds", defaultValue = "{}")
        var clouds: Clouds,

        @ColumnInfo(name = "rain", defaultValue = "{}")
        var rain: Rain,

        @ColumnInfo(name = "snow", defaultValue = "{}")
        var snow: Snow,

        var dt: String,
        var sys: Sys,
        @ColumnInfo(name = "timeZone", defaultValue = "000000")
        var timeZone: String,
        var id: String,
        var name: String, var cod: String
): Parcelable


@Parcelize
data class Coord(var lon: String, var lat: String): Parcelable

@Parcelize
data class Weather(var id: String, var main: String, var description: String, var icon: String): Parcelable

@Parcelize
data class Main(
        var temp: String,
        var feels_like: String,
        var pressure: String,
        var humidity: String,
        var temp_min: String,
        var temp_max: String,
        var sea_level: String,
        var grnd_level: String
): Parcelable

@Parcelize
data class Wind(var speed: String,
                var deg: String,
                var gust: String): Parcelable

@Parcelize
data class Clouds(var all: String): Parcelable

@Parcelize
data class Rain(var _1h: String,var _3h: String): Parcelable

@Parcelize
data class Snow(var _1h: String,var _3h: String): Parcelable

@Parcelize
data class Sys(var type: String,
               var id: String,
               var message: String,
               var country: String,
               var sunrise: String,
               var sunset: String): Parcelable
