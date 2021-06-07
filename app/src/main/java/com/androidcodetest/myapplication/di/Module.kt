package com.androidcodetest.myapplication.di

import android.app.Application
import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Config.DEBUG
import androidx.room.Room
import com.androidcodetest.myapplication.R
import com.androidcodetest.myapplication.datasource.localdatasource.WeatherDatabase
import com.androidcodetest.myapplication.datasource.localdatasource.database.WeatherDao
import com.androidcodetest.myapplication.datasource.remotedatasource.WeatherRemoteDataSourceApi
import com.androidcodetest.myapplication.repository.Weatherepository
import com.androidcodetest.myapplication.ui.main.MainViewModel
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val apiModule = module {

    fun provideWeatherApi(retrofit: Retrofit): WeatherRemoteDataSourceApi {
        return retrofit.create(WeatherRemoteDataSourceApi::class.java)
    }
    single { provideWeatherApi(get()) }

}

val databaseModule = module {

    fun provideDatabase(application: Application): WeatherDatabase {
        return Room.databaseBuilder(application, WeatherDatabase::class.java, "weather")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return  database.weatherDAO
    }

    single { provideDatabase(androidApplication()) }
    single { provideWeatherDao(get()) }
}

val networkModule = module {
    val connectTimeout : Long = 40// 20s
    val readTimeout : Long  = 40 // 20s

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
        if (DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        var apiInterceptor=  Interceptor(){
            val original = it.request()
            val originalUrl = original.url
            val url = originalUrl.newBuilder()
                    .addQueryParameter("appid", "123455678990")
                    .build()
            val requestBuilder = original.newBuilder().url(url)
            val request= requestBuilder.build()
            return@Interceptor it.proceed(request)
        }

        okHttpClientBuilder.addInterceptor(apiInterceptor)
        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    single { provideHttpClient() }
    single {
        val baseUrl = androidContext().getString(R.string.BASE_URL)
        provideRetrofit(get(), baseUrl)
    }
}

val repositoryModule = module {
    fun provideCountryRepository(api: WeatherRemoteDataSourceApi, context: Context, dao : WeatherDao): Weatherepository {
        return Weatherepository(api, context, dao)
    }
    single { provideCountryRepository(get(), androidContext(), get()) }
}


val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
        MainViewModel(get(),get())
    }

}