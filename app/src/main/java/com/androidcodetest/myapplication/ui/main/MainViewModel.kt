package com.androidcodetest.myapplication.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.androidcodetest.myapplication.data.WeatherByCity
import com.androidcodetest.myapplication.repository.IWeatherRepository
import com.androidcodetest.myapplication.repository.WeatherRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map

public class MainViewModel( application: Application, val weatherepository: IWeatherRepository) : AndroidViewModel(application) {

   var searchZipOrCity = MutableLiveData<String>("london")
   var weathers: LiveData<List<WeatherByCity>?>? = null
   val repeatFun = repeatFun()

   init {
      viewModelScope.launch (Dispatchers.IO) {
         // Do things!
         weathers =
            weatherepository.getWeathersOrderInLastTouch()
                    ?.map { it.getOrNull()?.weathers }?.asLiveData()
         }


//start the loop

      repeatFun.start()
   }

   fun repeatFun(): Job {
      return viewModelScope.launch {
         while (isActive) {
            update()
            delay(1000 * 60 * 20)
         }
      }
   }

   fun update() {
      viewModelScope.launch(Dispatchers.IO)  {
         weathers?.value?.forEach {weatherepository.updateWeatherByCity(it.name)  }
      }
   }

   fun getWeatherByCityByUser(){
      viewModelScope.launch(Dispatchers.IO)  {
         searchZipOrCity.value?.let { weatherepository.updateWeatherByCity(it)
            weatherepository.updateLastTouchByCity(it)
         }

      }
   }
   fun getWeatherByZipCodeByUser(){
      viewModelScope.launch (Dispatchers.IO) {
         searchZipOrCity.value?.let { weatherepository.updateWeatherByZipCode(it)

            weatherepository.updateLastTouchByZipCode(it)
         }
      }
   }
}