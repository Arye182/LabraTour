//package com.example.labratour
//import android.app.Application
//import com.google.firebase.FirebaseApp
//
//import org.koin.android.ext.koin.androidContext
//import org.koin.android.ext.koin.androidLogger
//import org.koin.core.context.startKoin
//import org.koin.core.logger.Level
//
//class LabraTourApp : Application (){
//    companion object {
//        lateinit var instance: Application
//            private set
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        instance = this
//        FirebaseApp.initializeApp(this)
//        startKoin {
//            androidContext(this@LabraTourApp)
//            if (BuildConfig.DEBUG) androidLogger(Level.DEBUG)
//            modules(appModules + domainModules + dataModules)
//        }
//    }
//}
//val appModules = listOf(presentationModule)
//val domainModules = listOf(interactionModule)
//val dataModules = listOf(networkingModule, repositoryModule, databaseModule)