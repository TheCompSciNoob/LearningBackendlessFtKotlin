package com.example.per6.learningbackendlessftkotlin

import android.app.Application
import com.backendless.Backendless

/**
 * Created by per6 on 2/14/18.
 */
class BackendlessApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Backendless.initApp(this, BackendSettings.APP_ID,  BackendSettings.API_KEY);
    }
}