package com.wahyusembiring.roomksp2.di

import android.content.Context
import com.wahyusembiring.roomksp2.data.local.HomeworkDao
import com.wahyusembiring.roomksp2.data.local.MainDatabase

interface AppModule {
   val homeworkDao: HomeworkDao
}

class AppModuleImpl(
   private val appContext: Context
) : AppModule {

   private val mainDatabase by lazy {
      MainDatabase.getSingleton(appContext)
   }

   override val homeworkDao: HomeworkDao by lazy {
      mainDatabase.homeworkDao()
   }
}