package com.example.labratour.presentation

import android.app.Application
import com.example.labratour.presentation.di.AppContainer
import com.google.firebase.FirebaseApp

// Custom Application class that needs to be specified
// in the AndroidManifest.xml file
class LabratourApplication : Application() {
    lateinit var appContainer: AppContainer

    companion object {
        lateinit var instance: Application
            private set
    }
    // Instance of AppContainer that will be used by all the Activities of the app

    override fun onCreate() {
        super.onCreate()
        instance = this
        FirebaseApp.initializeApp(this)
        appContainer = AppContainer()
    }
}
