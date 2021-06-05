package com.androidcodetest.myapplication.datasource.localdatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androidcodetest.myapplication.data.*
import com.androidcodetest.myapplication.datasource.localdatasource.database.WeatherDao


@Database(
    entities = [Coord::class, Weather::class, Main::class, Wind::class, Clouds::class,Rain::class, Snow::class, Sys::class],
    version = 1, exportSchema = false
)

@TypeConverters(Converters::class, LanguagesTypeConverter::class, NameConverter::class, NativeConverter::class, TranslationsConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDAO:WeatherDao
}