package com.wahyusembiring.roomksp2

import android.app.Application
import com.wahyusembiring.roomksp2.di.AppModule
import com.wahyusembiring.roomksp2.di.AppModuleImpl

class App : Application() {

   companion object {
      lateinit var appModule: AppModule
   }

   override fun onCreate() {
      super.onCreate()
      appModule = AppModuleImpl(this)
   }
}