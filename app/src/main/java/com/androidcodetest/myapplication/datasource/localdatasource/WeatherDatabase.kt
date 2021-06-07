package com.androidcodetest.myapplication.datasource.localdatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.androidcodetest.myapplication.data.*
import com.androidcodetest.myapplication.datasource.localdatasource.database.WeatherDao
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Database(
        entities = [WeatherByCity::class],
        version = 5, exportSchema = false
)

@TypeConverters(
    WeatherByCityConverters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDAO: WeatherDao
}



class WeatherByCityConverters {

    @TypeConverter
    fun fromString(value: String?): ArrayList<Weather> {
        val listType =object : TypeToken<ArrayList<Weather>>(){}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromArrayList(list: ArrayList<Weather?>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun StringToSys(string: String?): Sys? {
        return string?.let {
            Gson().fromJson(string, Sys::class.java)
        }
    }

    @TypeConverter
    fun SysToString(sys: Sys?): String? {
        return sys?.let {
            Gson().toJson(sys)
        }
    }
    @TypeConverter
    fun StringToSnow(string: String?): Snow? {
        return string?.let {
            Gson().fromJson(string, Snow::class.java)
        }
    }

    @TypeConverter
    fun SnowToString(snow: Snow?): String? {
        return snow?.let {
            Gson().toJson(snow)
        }
    }

    @TypeConverter
    fun StringToRain(string: String?): Rain? {
        return string?.let {
            Gson().fromJson(string, Rain::class.java)
        }
    }

    @TypeConverter
    fun RainToString(rain: Rain?): String? {
        return rain?.let {
            Gson().toJson(rain)
        }
    }

    @TypeConverter
    fun StringToClouds(string: String?): Clouds? {
        return string?.let {
            Gson().fromJson(string, Clouds::class.java)
        }
    }

    @TypeConverter
    fun CloudsToString(clouds: Clouds?): String? {
        return clouds?.let {
            Gson().toJson(clouds)
        }
    }

    @TypeConverter
    fun StringToWind(string: String?): Wind? {
        return string?.let {
            Gson().fromJson(string, Wind::class.java)
        }
    }

    @TypeConverter
    fun WindToString(wind: Wind?): String? {
        return wind?.let {
            Gson().toJson(wind)
        }
    }

    @TypeConverter
    fun StringToMain(string: String?): Main? {
        return string?.let {
            Gson().fromJson(string, Main::class.java)
        }
    }

    @TypeConverter
    fun MainToString(main: Main?): String? {
        return main?.let {
            Gson().toJson(main)
        }
    }

    @TypeConverter
    fun StringToWeather(string: String?): Weather? {
        return string?.let {
            Gson().fromJson(string, Weather::class.java)
        }
    }

    @TypeConverter
    fun WeatherToString(weather: Weather?): String? {
        return weather?.let {
            Gson().toJson(weather)
        }
    }

    @TypeConverter
    fun StringToCoord(string: String?): Coord? {
        return string?.let {
            Gson().fromJson(string, Coord::class.java)
        }
    }

    @TypeConverter
    fun CoordToString(coord: Coord?): String? {
        return coord?.let {
            Gson().toJson(coord)
        }
    }

}


class SysConverters {
    @TypeConverter
    fun StringToSys(string: String?): Sys? {
        return string?.let {
            Gson().fromJson(string, Sys::class.java)
        }
    }

    @TypeConverter
    fun SysToString(sys: Sys?): String? {
        return sys?.let {
            Gson().toJson(sys)
        }
    }
}

class SnowConverters {
    @TypeConverter
    fun StringToSnow(string: String?): Snow? {
        return string?.let {
            Gson().fromJson(string, Snow::class.java)
        }
    }

    @TypeConverter
    fun SnowToString(snow: Snow?): String? {
        return snow?.let {
            Gson().toJson(snow)
        }
    }

}

class RainConverters {
    @TypeConverter
    fun StringToRain(string: String?): Rain? {
        return string?.let {
            Gson().fromJson(string, Rain::class.java)
        }
    }

    @TypeConverter
    fun RainToString(rain: Rain?): String? {
        return rain?.let {
            Gson().toJson(rain)
        }
    }

}

class CloudsConverters {
    @TypeConverter
    fun StringToClouds(string: String?): Clouds? {
        return string?.let {
            Gson().fromJson(string, Clouds::class.java)
        }
    }

    @TypeConverter
    fun CloudsToString(clouds: Clouds?): String? {
        return clouds?.let {
            Gson().toJson(clouds)
        }
    }

}

class WindConverters {
    @TypeConverter
    fun StringToWind(string: String?): Wind? {
        return string?.let {
            Gson().fromJson(string, Wind::class.java)
        }
    }

    @TypeConverter
    fun WindToString(wind: Wind?): String? {
        return wind?.let {
            Gson().toJson(wind)
        }
    }

}

class MainConverters {
    @TypeConverter
    fun StringToMain(string: String?): Main? {
        return string?.let {
            Gson().fromJson(string, Main::class.java)
        }
    }

    @TypeConverter
    fun MainToString(main: Main?): String? {
        return main?.let {
            Gson().toJson(main)
        }
    }

}

class WeatherConverters {
    @TypeConverter
    fun StringToWeather(string: String?): Weather? {
        return string?.let {
            Gson().fromJson(string, Weather::class.java)
        }
    }

    @TypeConverter
    fun WeatherToString(weather: Weather?): String? {
        return weather?.let {
            Gson().toJson(weather)
        }
    }

}

class CoordConverters {
    @TypeConverter
    fun StringToCoord(string: String?): Coord? {
        return string?.let {
            Gson().fromJson(string, Coord::class.java)
        }
    }

    @TypeConverter
    fun CoordToString(coord: Coord?): String? {
        return coord?.let {
            Gson().toJson(coord)
        }
    }

}

