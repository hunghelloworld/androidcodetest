package com.androidcodetest.myapplication.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.androidcodetest.myapplication.data.WeatherByCity
import com.androidcodetest.myapplication.repository.Weatherepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainViewModel(application: Application,var weatherepository: Weatherepository) : AndroidViewModel(application) {
   var searchZipOrCity = MutableLiveData<String>()
   var weathers: LiveData<List<WeatherByCity>?>? = null
   val repeatFun = repeatFun()

   init {
      viewModelScope.launch {
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
      viewModelScope.launch {
         weathers?.value?.forEach {weatherepository.updateWeatherByCity(it.name)  }
      }
   }

   fun getWeatherByCityByUser(){
      viewModelScope.launch {
         searchZipOrCity.value?.let { weatherepository.updateWeatherByCity(it)
            weatherepository.updateLastTouchByCity(it)
         }

      }
   }
   fun getWeatherByZipCodeByUser(){
      viewModelScope.launch {
         searchZipOrCity.value?.let { weatherepository.updateWeatherByZipCode(it)

            weatherepository.updateLastTouchByZipCode(it)
         }
      }
   }
}