package com.eniobiondic.noted

import android.app.Application
import di.initKoin
import org.koin.android.ext.koin.androidContext

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AndroidApplication)
        }
    }
}
